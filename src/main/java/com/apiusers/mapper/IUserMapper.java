package com.apiusers.mapper;

import com.apiusers.entity.UserEntity;
import com.apiusers.record.request.UserRequestRecord;
import com.apiusers.record.response.UserResponseRecord;
import org.mapstruct.Mapper;

@Mapper
public interface IUserMapper {
    UserEntity userRequestRecordToUserEntity(UserRequestRecord userRequestRecord);
    UserResponseRecord userEntityToUserResponseRecord(UserEntity userEntity);
}
