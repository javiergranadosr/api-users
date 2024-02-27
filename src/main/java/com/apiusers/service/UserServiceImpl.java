package com.apiusers.service;

import com.apiusers.entity.RoleEntity;
import com.apiusers.entity.UserEntity;
import com.apiusers.exception.ErrorDuplicateKey;
import com.apiusers.exception.ErrorNotFound;
import com.apiusers.mapper.IUserMapper;
import com.apiusers.record.request.UserRequestRecord;
import com.apiusers.record.response.ResponseMessageRecord;
import com.apiusers.record.response.UserResponseRecord;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@AllArgsConstructor
@Service
public class UserServiceImpl  implements IUserService{

    private final IUserMapper mapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Page<UserResponseRecord> findAllWithPagination(int page, int size, String orderBy, String order) {
        log.info("UserServiceImpl call method findAllWithPagination()");
        Sort.Direction direction = order.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Page<UserEntity> users = this.userRepository.findAll(PageRequest.of(page, size, Sort.by(direction, orderBy)));
        return users.map( u -> this.mapper.userEntityToUserResponseRecord(u));
    }

    @Override
    public List<UserResponseRecord> findAll() {
        log.info("UserServiceImpl call method findAll()");
        List<UserEntity> users = this.userRepository.findAll();
        return users.stream().map( u -> this.mapper.userEntityToUserResponseRecord(u)).collect(Collectors.toList());
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
        user.setName(data.name());
        user.setLastname(data.lastname());
        user.setEmail(data.email());
        user.setTelephone(data.telephone());

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
