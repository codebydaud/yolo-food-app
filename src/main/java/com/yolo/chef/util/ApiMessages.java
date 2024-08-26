package com.yolo.chef.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiMessages {
    RECIPE_NOT_FOUND("Recipe does not exist"),
    IDEA_NOT_FOUND("Idea does not exist");
    private final String message;

}