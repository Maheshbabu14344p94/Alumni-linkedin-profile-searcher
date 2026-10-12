package com.assignment.alumni.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record AlumniSearchRequest(
    @NotBlank(message = "University is required")
    @Size(max = 300, message = "University must be at most 300 characters")
    String university,
    @NotBlank(message = "Designation is required")
    @Size(max = 250, message = "Designation must be at most 250 characters")
    String designation,
    @Min(value = 1950, message = "Pass-out year must be 1950 or later")
    @Max(value = 2100, message = "Pass-out year must be 2100 or earlier")
    Integer passoutYear
) {}
