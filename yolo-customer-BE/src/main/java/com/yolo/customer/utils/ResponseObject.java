package com.yolo.customer.utils;

import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ResponseObject<T> {
    private Boolean success;
    private Map<String, T> data;

    public ResponseObject(Boolean success, String name, T data) {
        this.success= success;
        this.data = new HashMap<>();
        this.data.put(name, data);
    }

    public static <T> Map<String, T> createDataResponse(String name, T data) {
        Map<String, T> responseData = new HashMap<>();
        responseData.put(name, data);
        return responseData;
    }

    public static <T> ResponseObject<T> createSuccessResponse(String name, T data) {
        return new ResponseObject<>(true, name, data);
    }

    public static <T> Map<String, Object> createPaginatedResponse(String name, T data, int currentPage, int pageSize, long totalItems, int totalPages) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put(name, data);
        responseData.put("currentPage", currentPage);
        responseData.put("pageSize", pageSize);
        responseData.put("totalItems", totalItems);
        responseData.put("totalPages", totalPages);
        return responseData;
    }
}