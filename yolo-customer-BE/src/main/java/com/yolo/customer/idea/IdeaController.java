package com.yolo.customer.idea;

import com.yolo.customer.idea.dto.IdeaRequest;
import com.yolo.customer.idea.dto.IdeaResponse;
import com.yolo.customer.utils.ErrorResponse;
import com.yolo.customer.utils.ResponseObject;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/users/ideas")
public class IdeaController {

    @Autowired
    private IdeaService ideaService;

    @PreAuthorize("hasAuthority('ROLE_UPDATE_IDEA_STATUS')")
    @PatchMapping("/{ideaId}")
    public ResponseEntity<Map<String, String>> submitIdeaToVendor(@PathVariable("ideaId") Integer ideaId, @RequestBody Map<String, String> requestBody) {
        String status = requestBody.get("status");
        return ideaService.submitIdeaToVendor(ideaId, status);
    }

    @PreAuthorize("hasAuthority('CREATE_IDEA')")
    @PostMapping("/create-draft")
    public ResponseEntity<?> createDraftIdea(@RequestBody IdeaRequest request) {
        try {
            Idea idea = ideaService.createDraftIdea(request);
            return ResponseEntity.ok(new ResponseObject<>(true, "idea", idea));
        } catch (IllegalArgumentException e) {
            log.warn("Illegal argument: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ErrorResponse.create(HttpStatus.BAD_REQUEST, "Bad Request", e.getMessage()));
        } catch (Exception ex) {
            log.error("Internal server error: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.create(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('VIEW_ALL_IDEAS')")
    @GetMapping
    public ResponseEntity<?> getIdeas(
            @RequestParam(value = "status", required = false) Integer statusId,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortOrder", defaultValue = "desc") String sortOrder) {

        try {
            Page<IdeaResponse> ideas = ideaService.getIdeas(Optional.ofNullable(statusId), search, page, size, sortOrder);
            return ResponseEntity.ok(new ResponseObject<>(true, "ideas", ideas));
        } catch (IllegalArgumentException e) {
            log.warn("Illegal argument: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ErrorResponse.create(HttpStatus.BAD_REQUEST, "Bad Request", e.getMessage()));
        } catch (Exception ex) {
            log.error("Internal server error: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.create(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage()));
        }
    }
}
