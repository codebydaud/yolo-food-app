package com.yolo.customer.recipe;

import com.yolo.customer.recipe.dto.RecipeRequest;
import com.yolo.customer.recipe.dto.RecipeResponse;
import com.yolo.customer.utils.ErrorResponse;
import com.yolo.customer.utils.ResponseObject;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/users/ideas/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PreAuthorize("hasAuthority('ROLE_VIEW_ALL_RECIPES')")
    @GetMapping
    public ResponseEntity<?> getRecipeList(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "ideaId", required = false) Integer ideaId,
            @RequestParam(name = "search", required = false) String search) {
        try {
            List<RecipeResponse> recipeResponses = recipeService.findAll(page, size, ideaId, search);
            if (recipeResponses.isEmpty()) {
                return ResponseEntity.ok(new ResponseObject<>(true, "recipes", new ArrayList<>()));
            }
            return ResponseEntity.ok(new ResponseObject<>(true, "recipes", recipeResponses));
        } catch (IllegalArgumentException e) {
            log.warn("Illegal argument: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ErrorResponse.create(HttpStatus.BAD_REQUEST, "Bad Request", e.getMessage()));
        } catch (Exception ex) {
            log.error("Internal server error: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.create(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('ROLE_UPDATE_RECIPE_STATUS')")
    @PostMapping
    public ResponseEntity<?> createRecipe(@RequestBody RecipeRequest newRecipe) {
        try {
            Recipe recipe = recipeService.createRecipe(newRecipe);
            return new ResponseEntity<>(recipe, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.create(HttpStatus.NOT_FOUND, "Idea Not Found", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ErrorResponse.create(HttpStatus.BAD_REQUEST, "Invalid Request", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.create(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e.getMessage()));
        }
    }
}
