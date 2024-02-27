package com.apiusers.record.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoleRequestRecord(
        @NotBlank(message = "Name is mandatory")
        @Size(min = 4, max = 100)
        String name,
        @NotBlank(message = "Description is mandatory")
        @Size(min = 10, max = 255)
        String description) {}
