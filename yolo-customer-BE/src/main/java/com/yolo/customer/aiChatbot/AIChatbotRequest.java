package com.yolo.customer.aiChatbot;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AIChatbotRequest {
    private List<String> interests;
    private List<String> dietaryRestrictions;

}
