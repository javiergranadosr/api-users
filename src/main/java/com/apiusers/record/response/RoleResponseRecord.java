package com.apiusers.record.response;

import java.time.LocalDate;

public record RoleResponseRecord(
        Long id,
        String name,
        String description,
        LocalDate createdAt,
        LocalDate updatedAt) {}
