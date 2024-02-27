package com.apiusers.mapper;

import com.apiusers.entity.RoleEntity;
import com.apiusers.entity.UserEntity;
import com.apiusers.exception.ErrorNotFound;
import com.apiusers.record.request.UserRequestRecord;
import com.apiusers.record.response.UserResponseRecord;
import com.apiusers.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
@Component
@AllArgsConstructor
public class UserMapperImpl implements IUserMapper{

    private RoleRepository repository;

    @Override
    public UserEntity userRequestRecordToUserEntity(UserRequestRecord userRequestRecord) {
        if (userRequestRecord == null) {
            return null;
        }

        Set<RoleEntity> roles = new HashSet<>();
        for (Integer r : userRequestRecord.roles()) {
            RoleEntity role = this.repository.findById(Long.valueOf(r)).orElseThrow(
                    () -> new ErrorNotFound("Permission not found, try again.")
            );
            roles.add(role);
        }
        return new UserEntity(
                null,
                roles,
                userRequestRecord.name(),
                userRequestRecord.lastname(),
                userRequestRecord.username(),
                userRequestRecord.email(),
                userRequestRecord.telephone(),
                null,
                null
        );
    }

    @Override
    public UserResponseRecord userEntityToUserResponseRecord(UserEntity userEntity) {
        if (userEntity == null) {return null;}

        Set<String> rolesDescription = new HashSet<>();
        userEntity.getRoles().forEach( r -> rolesDescription.add(r.getDescription()));

        return new UserResponseRecord(
                userEntity.getId(),
                rolesDescription,
                userEntity.getName(),
                userEntity.getLastname(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getTelephone(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt()
        );
    }
}
