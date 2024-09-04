package com.yolo.chef.idea;

import com.yolo.chef.dto.IdeaDetailResponse;
import com.yolo.chef.dto.IdeaGetResponse;
import com.yolo.chef.dto.IdeaPostRequest;
import com.yolo.chef.exception.NotFoundException;
import com.yolo.chef.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ideas")

@CrossOrigin
public class IdeaController {
    @Autowired
    private IdeaService ideaService;

    @PreAuthorize("hasRole('ROLE_VIEW_IDEA_DETAIL')")
    @GetMapping("/{idea_id}")
    public ResponseEntity<?> findById(@PathVariable Integer idea_id) {
        try {
            IdeaDetailResponse ideaDetail =ideaService.findById(idea_id);
            return ResponseEntity.status(HttpStatus.OK).body(ideaDetail);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Bad Request");
            response.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (UnauthorizedException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Unauthorized access");
            response.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch(NotFoundException e){
            Map<String, String> response = new HashMap<>();
            response.put("message", "Not Found");
            response.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Internal server error");
            response.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
    @PreAuthorize("hasRole('ROLE_VIEW_IDEAS')")


    @GetMapping
    public ResponseEntity<?> getAllIdeas(@RequestParam(required = false) String title ,@RequestParam(name = "page", defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", defaultValue = "1000") Integer size) {
        try{
          IdeaGetResponse ideas = ideaService.getAllIdeas(Optional.ofNullable(title),page,size);
           return ResponseEntity.status(HttpStatus.OK).body(ideas);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Bad Request");
            response.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (UnauthorizedException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Unauthorized access");
            response.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }catch(NotFoundException e){
            Map<String, String> response = new HashMap<>();
            response.put("message", "Not Found");
            response.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Internal server error");
            response.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
    @PreAuthorize("hasRole('ROLE_VIEW_IDEAS')")

    @PostMapping
    public ResponseEntity<Map<String, String>> createIdea(@RequestBody IdeaPostRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            System.out.println("req  "+request.getIdea().getCustomerName());
            System.out.println("req  "+request.getIdea().getDescription());

            ideaService.createIdea(request);
            response.put("message", "Idea submitted successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("message", "Bad Request");
            response.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (UnauthorizedException e) {
            response.put("message", "Unauthorized access");
            response.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (Exception e) {
            response.put("message", "Internal server error");
            response.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}