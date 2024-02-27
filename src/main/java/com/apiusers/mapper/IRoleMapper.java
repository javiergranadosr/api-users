package com.apiusers.mapper;

import com.apiusers.entity.RoleEntity;
import com.apiusers.record.request.RoleRequestRecord;
import com.apiusers.record.response.RoleResponseRecord;
import org.mapstruct.Mapper;

@Mapper
public interface IRoleMapper {

    RoleEntity roleRequestRecordToRoleEntity(RoleRequestRecord roleRequestRecord);
    RoleResponseRecord roleEntityToRoleResponseRecord(RoleEntity roleEntity);



}
