package com.apiusers.record.request;

import jakarta.validation.constraints.*;

import java.util.Set;

public record UserRequestRecord(
        @NotBlank(message = "Name is mandatory")
        @Size(min = 4, max = 200)
        String name,
        @NotBlank(message = "Lastname is mandatory")
        @Size(min = 4, max = 200)
        String lastname,
        @NotBlank(message = "Username is mandatory")
        @Size(min = 4, max = 30)
        String username,
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Please provide a valid email address")
        @Size(max = 255)
        String email,

        @NotBlank(message = "Telephone is mandatory")
        @Size(max = 20)
        String telephone,

        @NotNull(message = "Phone code is mandatory")
        Long phoneCodeId,

        @NotEmpty(message = "Role is mandatory")
        Set<Integer> roles
) {
}
