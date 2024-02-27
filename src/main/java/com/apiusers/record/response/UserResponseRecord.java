package com.apiusers.record.response;

import java.time.LocalDate;
import java.util.Set;

public record UserResponseRecord(
        Long id,
        Set<String> roles,
        String name,
        String lastname,
        String username,
        String email,
        String telephone,
        LocalDate createdAt,
        LocalDate updatedAt
) {
}
