package com.apiusers.service;

import com.apiusers.entity.RoleEntity;
import com.apiusers.exception.ErrorNotFound;
import com.apiusers.mapper.IRoleMapper;
import com.apiusers.record.request.RoleRequestRecord;
import com.apiusers.record.response.ResponseMessageRecord;
import com.apiusers.record.response.RoleResponseRecord;
import com.apiusers.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Log4j2
@AllArgsConstructor
@Service
public class RoleServiceImpl  implements  IRoleService{
    private final RoleRepository repository;
    private final IRoleMapper roleMapper;

    @Override
    public Page<RoleResponseRecord> findAllWithPagination(int page, int size, String orderBy, String order) {
        log.info("RoleServiceImpl call method findAllWithPagination()");
        Sort.Direction direction = order.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Page<RoleEntity> data = this.repository.findAll(PageRequest.of(page, size, Sort.by(direction, orderBy)));
        return data.map(r-> this.roleMapper.roleEntityToRoleResponseRecord(r));
    }

    @Override
    public List<RoleResponseRecord> findAll() {
        log.info("RoleServiceImpl call method findAll()");
        List<RoleEntity> data = this.repository.findAll();
        return data.stream().map( r -> this.roleMapper.roleEntityToRoleResponseRecord(r)).collect(Collectors.toList());
    }

    @Override
    public RoleResponseRecord findById(Long id) {
        log.info("RoleServiceImpl call method findById()");
        RoleEntity roleEntity = this.repository.findById(id)
                .orElseThrow( () -> new ErrorNotFound("Permission not found try again"));
        return this.roleMapper.roleEntityToRoleResponseRecord(roleEntity);
    }

    @Transactional
    @Override
    public ResponseMessageRecord create(RoleRequestRecord data) {
        log.info("RoleServiceImpl call method create()");
        this.repository.save(this.roleMapper.roleRequestRecordToRoleEntity(data));
        return new ResponseMessageRecord(HttpStatus.CREATED.value(), "Permission created successfully");
    }

    @Transactional
    @Override
    public ResponseMessageRecord update(Long id, RoleRequestRecord data) {
        log.info("RoleServiceImpl call method update()");
        RoleEntity roleEntity = this.repository.findById(id)
                .orElseThrow(  () -> new ErrorNotFound("Permission not found, try again"));
        roleEntity.setName(data.name());
        roleEntity.setDescription(data.description());
        this.repository.save(roleEntity);
        return new ResponseMessageRecord(HttpStatus.OK.value(), "Permission updated successfully");
    }

    @Transactional
    @Override
    public ResponseMessageRecord delete(Long id) {
        log.info("RoleServiceImpl call method delete()");
        RoleEntity roleEntity = this.repository.findById(id)
                .orElseThrow( () -> new ErrorNotFound("Permission not found try again"));
        this.repository.delete(roleEntity);
        return new ResponseMessageRecord(HttpStatus.OK.value(), "Permission deleted successfully");
    }
}
