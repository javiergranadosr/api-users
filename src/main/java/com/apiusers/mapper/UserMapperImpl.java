package com.apiusers.mapper;

import com.apiusers.entity.FileEntity;
import com.apiusers.entity.PhoneCodeEntity;
import com.apiusers.entity.RoleEntity;
import com.apiusers.entity.UserEntity;
import com.apiusers.exception.ErrorNotFound;
import com.apiusers.record.request.UserRequestRecord;
import com.apiusers.record.response.RoleListResponseRecord;
import com.apiusers.record.response.UserResponseRecord;
import com.apiusers.repository.FileRepository;
import com.apiusers.repository.PhoneCodeRepository;
import com.apiusers.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Component
@AllArgsConstructor
public class UserMapperImpl implements IUserMapper{

    private final RoleRepository repository;
    private final PhoneCodeRepository phoneCodeRepository;
    private final FileRepository fileRepository;

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

        PhoneCodeEntity phoneCodeEntity = this.phoneCodeRepository.findById(userRequestRecord.phoneCodeId())
                .orElseThrow(() -> new ErrorNotFound("PhoneCode not found try again."));

        return new UserEntity(
                null,
                roles,
                userRequestRecord.name(),
                userRequestRecord.lastname(),
                userRequestRecord.username(),
                userRequestRecord.email(),
                userRequestRecord.telephone(),
                phoneCodeEntity,
                null,
                null
        );
    }

    @Override
    public UserResponseRecord userEntityToUserResponseRecord(UserEntity userEntity) {
        if (userEntity == null) {return null;}

        Optional<FileEntity> file = this.fileRepository.findByUserId(userEntity.getId());
        String fileDownloadUrl = "";
        if (file.isPresent()) {
            // Obtemos url de la imagen de perfil usuario
            fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/fileManager/files/")
                    .path(file.get().getId().toString())
                    .toUriString();
        }

        Set<RoleListResponseRecord> roles = new HashSet<>();
        userEntity.getRoles().forEach( r ->  roles.add(new RoleListResponseRecord(r.getId(), r.getDescription())));

        return new UserResponseRecord(
                userEntity.getId(),
                roles,
                userEntity.getName(),
                userEntity.getLastname(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getTelephone(),
                userEntity.getPhoneCodeEntity().getId(),
                fileDownloadUrl,
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt()
        );
    }
}
