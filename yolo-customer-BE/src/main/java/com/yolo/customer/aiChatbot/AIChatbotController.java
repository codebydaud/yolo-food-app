package com.yolo.customer.aiChatbot;

import com.yolo.customer.utils.ErrorResponse;
import com.yolo.customer.utils.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@CrossOrigin
@RestController
public class AIChatbotController {

    @Autowired
    private AIChatbotService aiService;

    @PreAuthorize("hasAuthority('CREATE_IDEA')")
    @PostMapping("/ai/generate")
    public ResponseEntity<?> generate(@RequestBody AIChatbotRequest requestBody) {
        try {
            Map<String, String> idea = aiService.generateIdea(requestBody);

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

    @PreAuthorize("hasAuthority('CREATE_IDEA')")
    @GetMapping("/ai/max-limit")
    public ResponseEntity<?> getMaxLimit() {
        try {
            int maxLimit = aiService.getMaxLimit();
            return ResponseEntity.ok(new ResponseObject<>(true, "maxLimit", maxLimit));
        } catch (Exception ex) {
            log.error("Internal server error: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.create(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage()));
        }
    }
}
