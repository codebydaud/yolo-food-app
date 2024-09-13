package com.yolo.customer.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AIChatbotUtils {

    private AIChatbotUtils() {
        // Private constructor to prevent instantiation
    }

    public static Map<String, String> extractTitleAndDescription(String result) {
        Pattern pattern = Pattern.compile("Title:\\s*\"(.*?)\"\\s*Description:\\s*(.*)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(result);

        String title = "";
        String description = "";

        if (matcher.find()) {
            title = matcher.group(1).trim();
            description = matcher.group(2).trim();
        }

        return Map.of(
                "title", title,
                "description", description
        );
    }

    public static String generatePrompt(String interests, String dietaryRestrictions) {
        return interests + "|" + (dietaryRestrictions.isEmpty() ? "none" : dietaryRestrictions);
    }
}
