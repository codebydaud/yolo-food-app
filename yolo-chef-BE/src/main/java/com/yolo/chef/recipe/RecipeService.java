package com.yolo.chef.recipe;

import com.yolo.chef.dto.RecipeDetailsResponseWrapper;
import com.yolo.chef.dto.RecipeListResponse;
import com.yolo.chef.dto.RecipeRequestForCustomerApp;
import com.yolo.chef.exception.BadRequestException;
import com.yolo.chef.exception.RecipeNotFoundException;
import com.yolo.chef.exception.RecipeStatusInvalidException;
import com.yolo.chef.idea.IdeaService;
import com.yolo.chef.mapper.RecipeRequestForCustomerAppMapper;
import com.yolo.chef.recipeImage.RecipeImage;
import com.yolo.chef.recipeImage.RecipeImageRepository;
import com.yolo.chef.recipeImage.RecipeImageService;
import com.yolo.chef.recipeStatus.RecipeStatus;
import com.yolo.chef.recipeStatus.RecipeStatusService;
import com.yolo.chef.user.User;
import com.yolo.chef.user.UserRepository;
import com.yolo.chef.util.ApiMessages;
import com.yolo.chef.util.LoggedinUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecipeService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final IdeaService ideaService;
    private final RecipeImageService recipeImageService;
    private final RecipeStatusService recipeStatusService;
    private final RecipeImageRepository recipeImageRepository;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 5;
    private static final Random RANDOM = new SecureRandom();

    public String generateUniqueCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }

    public Integer getRecipeCount(Integer ID)
    {
        List<Recipe> recipesList=recipeRepository.findByIdeaId(ID);
        Integer recipeCount=recipesList.size();
        return recipeCount;
    }
    public Recipe createRecipe(RecipeRequest recipeRequest,Integer ideaId)
    {
        if (recipeRequest.getPrice().compareTo(BigInteger.ZERO) <= 0) {
            throw new BadRequestException(
                    "Invalid price provided.",
                    "The price must be greater than zero. Provided value: " + recipeRequest.getPrice()
            );
        }
        if (recipeRequest.getServing_size()<= 0) {
            throw new BadRequestException(
                    "Invalid Serving provided.",
                    "The serving size must be greater than zero. Provided value: " + recipeRequest.getServing_size()
            );
        }
        if (recipeRequest.getImages() == null || recipeRequest.getImages().length == 0)
        {
            throw new BadRequestException(
                    "Invalid Images ",
                    "The Recipe must have atleast 1 image"
            );
        }
        Recipe recipe=new Recipe();
        recipe.setName(recipeRequest.getName());
        recipe.setDescription(recipeRequest.getDescription());
        recipe.setPrice( recipeRequest.getPrice().multiply(BigInteger.valueOf(100)));
        recipe.setServingSize(recipeRequest.getServing_size());
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setUpdatedAt(LocalDateTime.now());
        Optional<User> user= userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        recipe.setUserId(user.get().getId());
        recipe.setIdeaId(ideaId);
        String uniquecode="RCP"+generateUniqueCode();
        recipe.setCode(uniquecode);
        recipe.setRecipeStatusId(1);
        recipe.setCurrencyId(1);
        recipeRepository.save(recipe);
        for(int i=0;i<recipeRequest.getImages().length;i++)
        {
            RecipeImage recipeImage=new RecipeImage();

            String url=saveImageToStorage(recipeRequest.getImages()[i]);
            recipeImage.setUrl(url);
            recipeImage.setCreatedAt(LocalDateTime.now());
            recipeImage.setUpdatedAt(LocalDateTime.now());
            recipeImage.setRecipeId(recipe.getId());
            recipeImageRepository.save(recipeImage)  ;
        }
        return recipe;
    }

    public String saveImageToStorage(MultipartFile imageFile) {
        String uploadDirectory = "C://Users/muhammad.daud.rizvi/Desktop/RecipeImages";

        // Get the last recipe number from the database
        String lastImagePath = recipeImageRepository.findLastRecordById()
                .map(RecipeImage::getUrl) // Use getUrl to get the URL field
                .orElse(null); // Return null if no record is found

        int extractedNumber = 0;

        if (lastImagePath != null) {
            int firstDashIndex = lastImagePath.lastIndexOf("Recipe-") + 7;

            // Find the next '-' after the firstDashIndex
            int nextDashIndex = lastImagePath.indexOf('-', firstDashIndex);

            // Extract the substring between the first '-' and the next '-'
            String extractedNumber1 = lastImagePath.substring(firstDashIndex, nextDashIndex);


            extractedNumber = Integer.parseInt(extractedNumber1);
        }

        // Increment the recipe number, starting at 1 if no record exists
        int nextRecipeNumber = extractedNumber + 1;

        // Extract the file extension from the original filename
        String originalFilename = imageFile.getOriginalFilename();
        String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf('.')) : "";

        String fileNameWithoutExtension="";
        if (originalFilename != null) {
            int lastDotIndex = originalFilename.lastIndexOf('.');
            if (lastDotIndex > 0) { // Ensure there's at least one character before the dot
                fileNameWithoutExtension = originalFilename.substring(0, lastDotIndex);
            } else {
                fileNameWithoutExtension = originalFilename; // No extension found
            }
        }
        // Generate the new filename with the recipe number and file extension
        String nextRecipeFilename = "Recipe-" + nextRecipeNumber +"-"+ fileNameWithoutExtension+fileExtension;

        Path uploadPath = Path.of(uploadDirectory);
        Path filePath = uploadPath.resolve(nextRecipeFilename);

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image file", e);
        }

        return filePath.toString(); // Return the full path or just the filename depending on your need
    }

    public Optional<Recipe> updateRecipe(RecipeRequest recipeRequest,Integer recipeId) {
        if (recipeRequest.getPrice().compareTo(BigInteger.ZERO) <= 0) {
            throw new BadRequestException(
                    "Invalid price provided.",
                    "The price must be greater than zero. Provided value: " + recipeRequest.getPrice()
            );
        }
        if (recipeRequest.getServing_size()<= 0) {
            throw new BadRequestException(
                    "Invalid Serving provided.",
                    "The serving size must be greater than zero. Provided value: " + recipeRequest.getServing_size()
            );
        }
        Optional<Recipe> existing=recipeRepository.findById(recipeId);
        if (existing.isPresent()) {
            Recipe recipe=existing.get();
            System.out.println(recipe.getPrice());
            System.out.println(recipeRequest.getPrice());
            if(!recipeRequest.getName().equalsIgnoreCase(recipe.getName()))
            {
                System.out.println("Updated Name");
                recipe.setName(recipeRequest.getName());
            }
            //!= null && !recipeRequest.getDescription().isEmpty()
            if (!recipeRequest.getDescription() .equalsIgnoreCase(recipe.getDescription())) {
                System.out.println("Updated Description");
                recipe.setDescription(recipeRequest.getDescription());
            }
            if (!recipeRequest.getPrice() .equals(recipe.getPrice())) {
                System.out.println("Updated Price");
                recipe.setPrice(recipeRequest.getPrice().multiply(BigInteger.valueOf(100)));
            }
            if (!recipeRequest.getServing_size().equals(recipe.getServingSize())) {
                System.out.println("Updated ServingSize");
                recipe.setServingSize(recipeRequest.getServing_size());
            }
            List<String> storedImages=recipeImageService.getAllUrlAgainstId(recipe.getId());
//           for(int i=0;i<recipeRequest.getImages().length;i++)
//           {
//
//           }
            recipe.setUpdatedAt(LocalDateTime.now());
            recipeRepository.save(recipe);
            return Optional.of(recipe);
        }
        else {
            return Optional.empty();
        }

    }

//    public RecipeListResponse getAllRecipesByChef(Integer ideaId, String status, String sortOrder) {
//        List<Recipe> recipes;
//        String username= LoggedinUser.getUserName();
//        Optional<User> user=userRepository.findByUsername(username);
//        if (status != null) {
//            Integer statusId = recipeStatusService.findStatusIdByName(status);
//            recipes = recipeRepository.findByUserIdAndIdeaIdAndRecipeStatusId(user.get().getId(), ideaId, statusId);
//        } else {
//            recipes = recipeRepository.findByUserIdAndIdeaId(user.get().getId(), ideaId);
//        }
//
//        if ("desc".equalsIgnoreCase(sortOrder)) {
//            recipes.sort((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt()));
//        } else {
//            recipes.sort((r1, r2) -> r1.getCreatedAt().compareTo(r2.getCreatedAt()));
//        }
//
//        return new RecipeListResponse(recipes, ideaService, recipeImageService, recipeStatusService);
//    }

    public RecipeListResponse getAllRecipesByChef(Integer ideaId, String status, String sortOrder) {
        List<Recipe> recipes;
        String username = LoggedinUser.getUserName();
        Optional<User> user = userRepository.findByUsername(username);

        // Fetch the recipes based on the provided status and ideaId
        if (status != null) {
            Integer statusId = recipeStatusService.findStatusIdByName(status);
            recipes = recipeRepository.findByUserIdAndIdeaIdAndRecipeStatusId(user.get().getId(), ideaId, statusId);
        } else {
            recipes = recipeRepository.findByUserIdAndIdeaId(user.get().getId(), ideaId);
        }

        // Assuming you have a method to get the archived status ID
        Integer archivedStatusId = recipeStatusService.findStatusIdByName("Archived");

        // Filter out archived recipes
        recipes = recipes.stream()
                .filter(recipe -> !recipe.getRecipeStatusId().equals(archivedStatusId))
                .collect(Collectors.toList());

        // Sort recipes based on the provided sortOrder
        if ("desc".equalsIgnoreCase(sortOrder)) {
            recipes.sort((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt()));
        } else {
            recipes.sort((r1, r2) -> r1.getCreatedAt().compareTo(r2.getCreatedAt()));
        }

        return new RecipeListResponse(recipes, ideaService, recipeImageService, recipeStatusService);
    }



    public RecipeDetailsResponseWrapper getRecipeDetailsByRecipeId(Integer recipeId)
    {
        String username=LoggedinUser.getUserName();
        Optional<User> user=userRepository.findByUsername(username);

        Optional<Recipe> recipe = recipeRepository.findByUserIdAndId(user.get().getId(), recipeId);
        if(recipe.isPresent())
        {
            return new RecipeDetailsResponseWrapper(recipe.get(), ideaService, recipeImageService, recipeStatusService);
        }
        else {
            throw new RecipeNotFoundException(ApiMessages.RECIPE_NOT_FOUND.getMessage(),"The Recipe Against Recipe Id : " + recipeId +" Not Found" );
        }

    }
    public ResponseEntity<Map<String, String>> updateRecipeStatus(Integer recipeId, String status)
    {
        if(status.equalsIgnoreCase("draft") || status.isEmpty())
        {
            throw new RecipeStatusInvalidException(String.format(ApiMessages.RECIPE_STATUS_INVALID_ERROR.getMessage(), status), "Please give correct status");
        }

        String username=LoggedinUser.getUserName();
        Optional<User> user=userRepository.findByUsername(username);
        Optional<Recipe> recipe = recipeRepository.findByUserIdAndId(user.get().getId(), recipeId);

        if(recipe.isPresent())
        {
            if(recipe.get().getRecipeStatusId()==recipeStatusService.findStatusIdByName("submitted"))
            {
                throw new RecipeStatusInvalidException("Recipe is already submitted", "Please don't try to submit again");
            }
            RecipeRequestForCustomerAppMapper.printRecipeAsJson(new RecipeRequestForCustomerApp(recipe.get(), ideaService, recipeImageService));
            Integer statusId = recipeStatusService.findStatusIdByName(status);
            if(statusId==null)
            {
                throw new RecipeStatusInvalidException(String.format(ApiMessages.RECIPE_STATUS_INVALID_ERROR.getMessage(), status), "Please give correct status");
            }
            recipe.get().setRecipeStatusId(statusId);
            recipeRepository.save(recipe.get());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Recipe status updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            throw new RecipeNotFoundException(ApiMessages.RECIPE_NOT_FOUND.getMessage(),"The Recipe Against Recipe Id : " + recipeId +" Not Found" );
        }
    }

    public ResponseEntity<Map<String, String>> deleteRecipe(Integer recipeId)
    {
        String username=LoggedinUser.getUserName();
        Optional<User> user=userRepository.findByUsername(username);
        Integer statusId = recipeStatusService.findStatusIdByName("Draft");
        Optional<Recipe> recipe = recipeRepository.findByUserIdAndIdAndRecipeStatusId(user.get().getId(), recipeId, statusId);
        if(recipe.isPresent())
        {
            statusId = recipeStatusService.findStatusIdByName("Archived");
            recipe.get().setRecipeStatusId(statusId);
            recipeRepository.save(recipe.get());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Recipe deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            throw new RecipeNotFoundException(ApiMessages.RECIPE_NOT_FOUND.getMessage(),"The Recipe Against Recipe Id : " + recipeId +" Not Found" );
        }
    }

    }
