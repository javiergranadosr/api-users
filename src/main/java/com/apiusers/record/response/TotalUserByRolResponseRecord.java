package com.apiusers.record.response;

import java.util.List;

public record TotalUserByRolResponseRecord(
        List<String> descriptionRoles,
        List<Integer> totalByRoles
) {
}
