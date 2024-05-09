package com.apiusers.record.response;

import java.time.LocalDate;
import java.util.Set;

public record UserResponseRecord(
        Long id,
        Set<RoleListResponseRecord> roles,
        String name,
        String lastname,
        String username,
        String email,
        String telephone,
        Long phoneCodeId,
        LocalDate createdAt,
        LocalDate updatedAt
) {
}
