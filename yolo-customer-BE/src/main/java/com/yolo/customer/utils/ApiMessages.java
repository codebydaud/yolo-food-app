package com.yolo.customer.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiMessages {
    IDEA_NOT_FOUND("Idea not found for code: %s"),
    IDEA_NOT_FOUND_BY_ID("Idea not found for ID: %d"),
    CURRENCY_NOT_FOUND_BY_ID("Currency not found for ID: %d"),
    NO_RECIPES_FOUND("No recipes found with the given criteria."),
    PAGE_INDEX_NEGATIVE("Page index must not be less than zero."),
    PAGE_SIZE_INVALID("Page size must be greater than zero."),
    INTERESTS_REQUIRED("At least one interest should be entered."),
    DEFAULT_IDEA_STATUS_NOT_FOUND("Default Idea Status not found."),
    MAX_LIMIT_REACHED("Max limit reached for repeated prompts.");


    private final String message;
}
