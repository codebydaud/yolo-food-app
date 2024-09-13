package com.yolo.customer.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yolo.customer.enums.Order_Status;
import com.yolo.customer.order.dto.OrderRequest;
import com.yolo.customer.order.dto.VendorOrderRequest;
import com.yolo.customer.order.orderItem.OrderItem;
import com.yolo.customer.order.orderItem.OrderItemRepository;
import com.yolo.customer.order.orderStatus.OrderStatus;
import com.yolo.customer.order.orderStatus.OrderStatusRepository;
import com.yolo.customer.order.orderStatus.OrderStatusService;
import com.yolo.customer.recipe.Recipe;
import com.yolo.customer.recipe.RecipeRepository;
import com.yolo.customer.user.User;
import com.yolo.customer.user.UserRepository;
import com.yolo.customer.address.Address;
import com.yolo.customer.address.AddressRepository;
import com.yolo.customer.userProfile.UserProfile;
import com.yolo.customer.userProfile.UserProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.yolo.customer.utils.GetContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderStatusService orderStatusService;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final AddressRepository addressRepository;

    public OrderService(OrderRepository orderRepository, OrderStatusRepository orderStatusRepository,
                        OrderItemRepository orderItemRepository, RecipeRepository recipeRepository,
                        UserRepository userRepository,  UserProfileRepository userProfileRepository,
                        AddressRepository addressRepository, OrderStatusService orderStatusService){
        this.orderRepository=orderRepository;
        this.orderStatusRepository=orderStatusRepository;
        this.orderItemRepository = orderItemRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.addressRepository = addressRepository;
        this.orderStatusService = orderStatusService;
    }

    public Page<Order> findAll(Integer page, Integer size, String status) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = GetContextHolder.getUsernameFromAuthentication(authentication);

        User loggedInUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with given username does not exists: " + username));

        if (page < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero.");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero.");
        }
        if (size > 1000) {
            size = 1000;
        }

        Integer userId= loggedInUser.getId();
        Pageable paging = PageRequest.of(page, size);
        Page<Order> pageOrders;

        if (status == null || status.isEmpty()) {
            pageOrders = orderRepository. findByUserIdOrderByCreatedAtDesc(userId,paging);
        } else {
            Order_Status orderStatus;
            try {
                orderStatus = Order_Status.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid order status: " + status);
            }

            OrderStatus statusObj = orderStatusRepository.findByCode(orderStatus.toString())
                    .orElseThrow(() -> new EntityNotFoundException("No status found for: " + status));

            pageOrders = orderRepository.findByOrderStatusIdAndUserIdOrderByCreatedAtDesc(statusObj.getId(), userId ,paging);
        }
        return pageOrders;
    }

    public void updateOrderStatus(String orderCode, String status) {

        Order order = orderRepository.findByCode(orderCode)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with code: " + orderCode));

        Order_Status orderStatusEnum;
        try {
            orderStatusEnum = Order_Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }

        OrderStatus orderStatus = orderStatusRepository.findByCode(orderStatusEnum.toString())
                .orElseThrow(() -> new EntityNotFoundException("No status found for: " + status));

        order.setOrderStatusId(orderStatus.getId());
        orderRepository.save(order);
    }

    @Transactional
    public boolean placeOrder(OrderRequest orderRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = GetContextHolder.getUsernameFromAuthentication(authentication);
        User loggedInUser = userRepository.findByUsername(username).get();

        if(loggedInUser == null) {
            throw new EntityNotFoundException("User with given username doesnot exists: " + username);
        }
        Integer userId = loggedInUser.getId();

        OrderRequest.OrderDto orderDto = orderRequest.getOrder();

        if (orderDto == null) {
            throw new IllegalArgumentException("Order cannot be empty.");
        }

        if (orderDto.getOrderItems() == null || orderDto.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Order items must not be empty.");
        }

        for (OrderRequest.OrderItemDto item : orderDto.getOrderItems()) {
            if (item.getPrice() == null || item.getPrice().compareTo(BigInteger.ZERO) < 0) {
                throw new IllegalArgumentException("Price should not be less than 0.");
            }
        }

        Map<String, List<OrderRequest.OrderItemDto>> itemsByChefCode = groupOrderItemsByChefCode(orderDto);

        List<VendorOrderRequest.OrderDetails> vendorOrders = createOrdersAndPrepareVendorOrders(itemsByChefCode, userId);

        boolean vendorApiSuccess = callVendorApi(vendorOrders);
        if (!vendorApiSuccess) {
            throw new RuntimeException("Vendor API call failed. ");
        }

        return true;
    }

    private Map<String, List<OrderRequest.OrderItemDto>> groupOrderItemsByChefCode(OrderRequest.OrderDto orderDto) {
        Map<String, List<OrderRequest.OrderItemDto>> itemsByChefCode = new HashMap<>();

        for (OrderRequest.OrderItemDto itemDto : orderDto.getOrderItems()) {
            Optional<Recipe> recipeOpt = recipeRepository.findById(itemDto.getRecipeId());
            if (recipeOpt.isPresent()) {
                Recipe recipe = recipeOpt.get();
                String chefCode = recipe.getChefCode();
                itemsByChefCode.computeIfAbsent(chefCode, k -> new ArrayList<>()).add(itemDto);
            } else {
                throw new EntityNotFoundException("Recipe with ID " + itemDto.getRecipeId() + " does not exist.");
            }
        }

        return itemsByChefCode;
    }

    private List<VendorOrderRequest.OrderDetails> createOrdersAndPrepareVendorOrders(
            Map<String, List<OrderRequest.OrderItemDto>> itemsByChefCode, Integer userId) {

        List<VendorOrderRequest.OrderDetails> vendorOrders = new ArrayList<>();

        for (Map.Entry<String, List<OrderRequest.OrderItemDto>> entry : itemsByChefCode.entrySet()) {
            String chefCode = entry.getKey();
            List<OrderRequest.OrderItemDto> items = entry.getValue();

            BigInteger totalPrice = calculateTotalPrice(items);
            String orderCode = generateUniqueCode();

            Order order = new Order();
            order.setCode(orderCode);
            order.setPrice(totalPrice);

            Integer orderStatusId = orderStatusService.findStatusIdByValue("Placed");
            order.setOrderStatusId(orderStatusId);

            order.setUserId(userId);

            Order savedOrder = orderRepository.save(order);

            for (OrderRequest.OrderItemDto itemDto : items) {
                OrderItem orderItem = new OrderItem();
                orderItem.setQuantity(itemDto.getQuantity());
                orderItem.setPrice(itemDto.getPrice());
                orderItem.setRecipeId(itemDto.getRecipeId());
                orderItem.setOrderId(savedOrder.getId());
                orderItemRepository.save(orderItem);
            }

            VendorOrderRequest vendorOrderRequest = prepareVendorOrderDetails(userId, orderCode, totalPrice, items);
            VendorOrderRequest.OrderDetails orderDetails = vendorOrderRequest.getOrder();
            vendorOrders.add(orderDetails);
        }

        return vendorOrders;
    }

    private VendorOrderRequest prepareVendorOrderDetails(Integer uId, String orderCode, BigInteger totalPrice,
                                                         List<OrderRequest.OrderItemDto> items) {

        VendorOrderRequest vendorOrderRequest = new VendorOrderRequest();

        VendorOrderRequest.OrderDetails orderDetails = new VendorOrderRequest.OrderDetails();
        orderDetails.setTotal_price(totalPrice);

        UserProfile userProfile = userProfileRepository.findByUserId(uId).orElseThrow(() ->
                new RuntimeException("User Profile not found for userId: " + uId));

        orderDetails.setOrder_code(orderCode);
        orderDetails.setCustomer_name(userProfile.getFirstName());
        orderDetails.setCustomer_contact_number(userProfile.getContactNumber());

        Address address = addressRepository.findById(userProfile.getAddressId()).orElseThrow(() ->
                new RuntimeException("Address not found for addressId: " + userProfile.getAddressId()));

        VendorOrderRequest.OrderDetails.Address vendorAddress = new VendorOrderRequest.OrderDetails.Address();
        vendorAddress.setHouse(address.getHouse());
        vendorAddress.setStreet(address.getStreet());
        vendorAddress.setArea(address.getArea());
        vendorAddress.setZip_code(address.getZipCode());
        vendorAddress.setCity(address.getCity());
        vendorAddress.setCountry(address.getCountry());

        orderDetails.setAddress(vendorAddress);

        List<VendorOrderRequest.OrderDetails.OrderItem> vendorOrderItems = items.stream()
                .map(item -> {
                    VendorOrderRequest.OrderDetails.OrderItem vendorItem = new VendorOrderRequest.OrderDetails.OrderItem();
                    vendorItem.setQuantity(item.getQuantity());
                    vendorItem.setPrice(item.getPrice());
                    vendorItem.setRecipe_code(String.valueOf(item.getRecipeId()));
                    return vendorItem;
                })
                .collect(Collectors.toList());

        orderDetails.setOrder_items(vendorOrderItems);
        vendorOrderRequest.setOrder(orderDetails);

        return vendorOrderRequest;
    }

    private boolean callVendorApi(List<VendorOrderRequest.OrderDetails> orders) {
        ObjectMapper objectMapper = new ObjectMapper();

        for (VendorOrderRequest.OrderDetails orderDetails : orders) {
            try {
                VendorOrderRequest vendorOrderRequest = new VendorOrderRequest();
                vendorOrderRequest.setOrder(orderDetails);

                String requestBody = objectMapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(vendorOrderRequest);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    private BigInteger calculateTotalPrice(List<OrderRequest.OrderItemDto> items) {
        return items.stream()
                .map(OrderRequest.OrderItemDto::getPrice)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }


    private String generateUniqueCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

}