package com.apiusers.mapper;

import com.apiusers.entity.RoleEntity;
import com.apiusers.record.request.RoleRequestRecord;
import com.apiusers.record.response.RoleResponseRecord;
import org.springframework.stereotype.Component;

@Component
public class RoleMapperImpl implements IRoleMapper {
    @Override
    public RoleEntity roleRequestRecordToRoleEntity(RoleRequestRecord roleRequestRecord) {
        if (roleRequestRecord == null) {
            return null;
        }
        return new RoleEntity(null, roleRequestRecord.name(), roleRequestRecord.description(), null, null);
    }

    @Override
    public RoleResponseRecord roleEntityToRoleResponseRecord(RoleEntity roleEntity) {
        if (roleEntity == null) {
            return null;
        }
        return new RoleResponseRecord(roleEntity.getId(), roleEntity.getName(), roleEntity.getDescription(), roleEntity.getCreatedAt(), roleEntity.getUpdatedAt());
    }
}
