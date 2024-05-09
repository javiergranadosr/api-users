package com.apiusers.service;

import com.apiusers.entity.PhoneCodeEntity;
import com.apiusers.entity.RoleEntity;
import com.apiusers.entity.UserEntity;
import com.apiusers.exception.ErrorDuplicateKey;
import com.apiusers.exception.ErrorNotFound;
import com.apiusers.mapper.IUserMapper;
import com.apiusers.record.request.UserRequestRecord;
import com.apiusers.record.response.ResponseMessageRecord;
import com.apiusers.record.response.UserResponseRecord;
import com.apiusers.repository.PhoneCodeRepository;
import com.apiusers.repository.RoleRepository;
import com.apiusers.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@AllArgsConstructor
@Service
public class UserServiceImpl  implements IUserService{

    private final IUserMapper mapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PhoneCodeRepository phoneCodeRepository;

    @Override
    public Page<UserResponseRecord> findAllWithPagination(int page, int size, String orderBy, String order) {
        log.info("UserServiceImpl call method findAllWithPagination()");
        Sort.Direction direction = order.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Page<UserEntity> users = this.userRepository.findAll(PageRequest.of(page, size, Sort.by(direction, orderBy)));
        return users.map(this.mapper::userEntityToUserResponseRecord);
    }

    @Override
    public List<UserResponseRecord> findAll(Long roleId) {
        log.info("UserServiceImpl call method findAll()");
        List<UserEntity> users = null;
        if (roleId > 0) {
            users = this.userRepository.findAllUsersByRoleId(roleId);
        }else {
            users = this.userRepository.findAll();
        }
        return users.stream().map(this.mapper::userEntityToUserResponseRecord).toList();
    }

    @Override
    public UserResponseRecord findById(Long id) {
        log.info("UserServiceImpl call method findById()");
        UserEntity userEntity = this.userRepository.findById(id).orElseThrow( () ->
                new ErrorNotFound("User not found, try again"));
        return this.mapper.userEntityToUserResponseRecord(userEntity);
    }

    @Transactional
    @Override
    public ResponseMessageRecord create(UserRequestRecord data) {
        log.info("UserServiceImpl call method create()");
        Optional<UserEntity> tempUser = this.userRepository.findByUsername(data.username());
        if (tempUser.isPresent()) {
            throw new ErrorDuplicateKey("Username is not available, try again with other username");
        }
        this.userRepository.save(this.mapper.userRequestRecordToUserEntity(data));
        return new ResponseMessageRecord(HttpStatus.CREATED.value(), "User created successfully");
    }

    @Transactional
    @Override
    public ResponseMessageRecord update(Long id, UserRequestRecord data) {
        log.info("UserServiceImpl call method update()");
        UserEntity user =  this.userRepository.findById(id).orElseThrow(() -> new ErrorNotFound("User not found try again"));
        PhoneCodeEntity phoneCodeEntity = this.phoneCodeRepository.findById(data.phoneCodeId()).orElseThrow(() -> new ErrorNotFound("Phonecode not found try again"));

        user.setName(data.name());
        user.setLastname(data.lastname());
        user.setEmail(data.email());
        user.setTelephone(data.telephone());
        user.setPhoneCodeEntity(phoneCodeEntity);

        Set<RoleEntity> roles = new HashSet<>();
        data.roles().forEach( r -> {
            RoleEntity role = this.roleRepository.findById(Long.valueOf(r)).orElseThrow(() -> new ErrorNotFound("Role not found try again"));
            roles.add(role);
        });

        user.setRoles(roles);
        this.userRepository.save(user);
        return new ResponseMessageRecord(HttpStatus.OK.value(), "User updated successfully");
    }

    @Transactional
    @Override
    public ResponseMessageRecord delete(Long id) {
        UserEntity user = this.userRepository.findById(id).orElseThrow(() -> new ErrorNotFound("User not found try again"));
        this.userRepository.delete(user);
        return new ResponseMessageRecord(HttpStatus.OK.value(), "User deleted successfully");
    }
}
