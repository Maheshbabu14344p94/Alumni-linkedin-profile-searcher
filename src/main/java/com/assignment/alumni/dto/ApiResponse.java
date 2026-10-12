package com.assignment.alumni.dto;

import java.util.List;

public record ApiResponse<T>(String status, T data, String message, List<ApiError> errors) {
    public static <T> ApiResponse<T> success(T data) { return new ApiResponse<>("success", data, null, null); }
    public static <T> ApiResponse<T> error(String message, List<ApiError> errors) {
        return new ApiResponse<>("error", null, message, errors);
    }
    public record ApiError(String field, String message) {}
}
