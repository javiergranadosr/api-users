package com.apiusers.record.response;

import java.time.LocalDate;

public record PhoneCodeResponseRecord(
        Long id,
        String countryName,
        String countryCode,
        LocalDate createdAt,
        LocalDate updatedAt
) {
}
