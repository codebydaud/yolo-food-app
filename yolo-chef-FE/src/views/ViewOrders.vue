<script setup>
import { ref, computed, onMounted } from 'vue';

// Dummy data for orders and order items
const dummyOrders = [
  {
    id: 1,
    code: 'ORD003',
    price: '$ 5.00',
    orderStatusId: 1, // Placed
    customerName: 'Muhammad Daud',
    deliveryAddress:'50 B Block Johar Town LahorePakistan',
    createdAt: '2024-01-01T12:00:00Z',
    updatedAt: '2024-01-02T12:00:00Z',
  },
  {
    id: 2,
    code: 'ORD002',
    price: '$ 80.00',
    orderStatusId: 2, // Processing
    customerName: 'Kabeer',
    deliveryAddress:'50 B Block Johar Town LahorePakistan',
    createdAt: '2024-02-01T12:00:00Z',
    updatedAt: '2024-02-02T12:00:00Z',
  },
  {
    id: 3,
    code: 'ORD001',
    price: '$ 100.00',
    orderStatusId: 3, // Dispatched
    customerName: 'Uzair',
    deliveryAddress:'50 B Block Johar Town LahorePakistan',
    createdAt: '2024-03-01T12:00:00Z',
    updatedAt: '2024-03-02T12:00:00Z',
  },
];

const dummyOrderItems = [
  {
    orderItem: { id: 1, quantity: 1, createdAt: '2024-01-01T12:00:00Z' },
    recipe: { name: 'Garlic Pasta', code: 'PAS001', description: 'A rich and creamy garlic parmesan pasta', price: '$ 5.00', servingSize: 1, totalPrice: '$ 5.00' },
  },
];

// Vue refs and state variables
const orderData = ref({ orders: [] });
const selectedStatus = ref('');
const showModal = ref(false);
const selectedOrder = ref({});

const orderStatusEnum = {
  1: 'Received',
  2: 'Processing',
  3: 'Dispatched',
};

// Simulate fetching orders from an API
async function fetchOrders() {
  orderData.value.orders = dummyOrders;
}

// Simulate fetching order items based on order ID
async function fetchOrderItems(orderId) {
  selectedOrder.value.order_items = dummyOrderItems;
}

// Computed property to filter orders by status
const filteredOrders = computed(() => {
  if (!selectedStatus.value) {
    return orderData.value.orders;
  }
  return orderData.value.orders.filter(
    (order) => order.orderStatusId === parseInt(selectedStatus.value)
  );
});

// Set the order status filter
function setStatusFilter(status) {
  selectedStatus.value = status;
}

// Show order details modal
function showOrderDetails(order) {
  selectedOrder.value = order;
  showModal.value = true;
  fetchOrderItems(order.id);
}

// Close the modal
function closeModal() {
  showModal.value = false;
  selectedOrder.value = {};
}

// Update the status of the order
function updateOrderStatus() {
  if (selectedOrder.value.orderStatusId < 3) {
    selectedOrder.value.orderStatusId += 1; // Increment status
  } else {
    selectedOrder.value.orderStatusId = 1; // Reset to Received
  }
}

// Mounted lifecycle hook
onMounted(() => {
  fetchOrders();
});
</script>

<template>
  <div class="container mx-auto p-4">
    <h1 class="text-2xl font-bold mb-4">Order History</h1>

    <!-- Filter Options -->
    <div class="mb-4 flex justify-end space-x-4">
      <button @click="setStatusFilter('1')"
        :class="selectedStatus === '1' ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700'"
        class="px-4 py-2 rounded-lg focus:outline-none w-32 text-center">
        {{ orderStatusEnum[1] }}
      </button>
      <button @click="setStatusFilter('2')"
        :class="selectedStatus === '2' ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700'"
        class="px-4 py-2 rounded-lg focus:outline-none w-32 text-center">
        {{ orderStatusEnum[2] }}
      </button>
      <button @click="setStatusFilter('3')"
        :class="selectedStatus === '3' ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700'"
        class="px-4 py-2 rounded-lg focus:outline-none w-32 text-center">
        {{ orderStatusEnum[3] }}
      </button>
      <button @click="setStatusFilter('')"
        :class="selectedStatus === '' ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700'"
        class="px-4 py-2 rounded-lg focus:outline-none w-32 text-center">
        All
      </button>
    </div>

    <!-- Order List -->
    <div class="bg-white shadow rounded-lg">
      <div class="grid grid-cols-5 gap-4 bg-gray-100 p-4 text-gray-700 font-semibold">
        <div>Order Code</div>
        <div>Amount</div>
        <div>Status</div>
        <div>Customer Name</div>
        <div></div> <!-- Empty header for the Details button -->
      </div>
      <div v-for="order in filteredOrders" :key="order.id" class="grid grid-cols-5 gap-4 p-4 border-b">
        <div>{{ order.code }}</div>
        <div>{{ order.price }}</div>
        <div>{{ orderStatusEnum[order.orderStatusId] }}</div>
        <div>{{ order.customerName }}</div>
        <div class="text-right">
          <button @click="showOrderDetails(order)" class="px-4 py-2 bg-blue-500 text-white rounded">Details</button>
        </div>
      </div>
    </div>

    <!-- Modal for Order Details -->
    <div v-if="showModal" class="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50">
      <div class="bg-white rounded-lg shadow-lg p-6 w-full max-w-3xl">
        <div class="flex justify-between items-center border-b pb-4">
          <h3 class="text-xl font-semibold">Order# {{ selectedOrder.code }}</h3>
          <button @click="closeModal" class="text-gray-600 hover:text-black">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>

        <div class="mt-4 grid grid-cols-2 gap-4">
          <!-- Order Information -->
          <div class="border p-4 rounded-lg shadow-sm">
            <p><strong>Amount:</strong> {{ selectedOrder.price }} USD</p>
            <p><strong>Customer Name:</strong> {{ selectedOrder.customerName }}</p>
            <p><strong>Order Date:</strong> {{ new Date(selectedOrder.createdAt).toLocaleDateString() }}</p>
          </div>
          <!-- Shipping Information -->
          <div class="border p-4 rounded-lg shadow-sm">
            <p><strong>Status:</strong> {{ orderStatusEnum[selectedOrder.orderStatusId] }}</p>
            <p><strong>Delivery Address:</strong> {{ selectedOrder.deliveryAddress }}</p>
            <p><strong>Delivery Date:</strong> {{ new Date(selectedOrder.updatedAt).toLocaleDateString() }}</p>
          </div>
        </div>

        <!-- Button to change status -->
        <div class="mt-4 text-right">
          <button @click="updateOrderStatus" class="px-4 py-2 bg-green-500 text-white rounded">Change Status</button>
        </div>

        <!-- Order Items -->
        <h4 class="font-semibold mt-6">Order Items</h4>
        <div class="border-t mt-2 pt-2">
          <div class="grid grid-cols-7 gap-4 bg-gray-100 p-4 text-gray-700 font-semibold">
            <div>Recipe</div>
            <div>Recipe code</div>
            <div>Description</div>
            <div>Quantity</div>
            <div>Price</div>
            <div>Serving Size</div>
            <div>Total Price</div>
          </div>
          <div v-for="item in selectedOrder.order_items" :key="item.id" class="grid grid-cols-7 gap-4 p-4 border-b">
            <div>{{ item.recipe.name }}</div>
            <div>{{ item.recipe.code }}</div>
            <div>{{ item.recipe.description }}</div>
            <div>{{ item.orderItem.quantity }}</div>
            <div>{{ item.recipe.price }}</div>
            <div>{{ item.recipe.servingSize }}</div>
            <div>{{ item.recipe.totalPrice }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Add your styles here */
</style>
