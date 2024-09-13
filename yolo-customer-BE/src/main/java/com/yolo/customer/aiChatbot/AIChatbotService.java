package com.yolo.customer.aiChatbot;

import com.yolo.customer.user.UserRepository;
import com.yolo.customer.user.User;
import com.yolo.customer.utils.AIChatbotUtils;
import com.yolo.customer.utils.ApiMessages;
import com.yolo.customer.utils.GetContextHolder;
import io.swagger.v3.oas.models.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AIChatbotService {

    @Value("${api.security.max_limit}")
    public int maxLimit;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnthropicChatModel chatModel;

    @Autowired
    private PromptBuilder promptBuilder;

    // In-memory store for tracking user prompts
    private final ConcurrentHashMap<Integer, List<String>> userPrompts = new ConcurrentHashMap<>();

    public Map<String, String> generateIdea(AIChatbotRequest requestBody) {
        String interests = String.join(", ", requestBody.getInterests());
        String dietaryRestrictions = String.join(", ", requestBody.getDietaryRestrictions());

        processPrompt(interests, dietaryRestrictions);

        String prompt = promptBuilder.buildPrompt(requestBody);

        String result = chatModel.call(prompt);

        return AIChatbotUtils.extractTitleAndDescription(result);
    }

    public int processPrompt(String interests, String dietaryRestrictions) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = GetContextHolder.getUsernameFromAuthentication(authentication);

        User loggedInUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with given username does not exists: " + username));

        Integer userId = loggedInUser.getId();

        String currentPrompt = AIChatbotUtils.generatePrompt(interests, dietaryRestrictions);

        List<String> prompts = userPrompts.getOrDefault(userId, new ArrayList<>());

        long matchingPrompts = prompts.stream().filter(p -> p.equals(currentPrompt)).count();

        if (matchingPrompts >= maxLimit) {
            throw new IllegalStateException(ApiMessages.MAX_LIMIT_REACHED.getMessage());
        }

        prompts.add(currentPrompt);
        userPrompts.put(userId, prompts);

        return maxLimit - (int) matchingPrompts;
    }

    public int getMaxLimit() {
        return maxLimit;
    }
}
