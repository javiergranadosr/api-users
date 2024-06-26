package com.apiusers.service;

import com.apiusers.entity.RoleEntity;
import com.apiusers.exception.ErrorNotFound;
import com.apiusers.mapper.IRoleMapper;
import com.apiusers.record.request.RoleRequestRecord;
import com.apiusers.record.response.ResponseMessageRecord;
import com.apiusers.record.response.RoleResponseRecord;
import com.apiusers.record.response.TotalUserByRolQueryResponseRecord;
import com.apiusers.record.response.TotalUserByRolResponseRecord;
import com.apiusers.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Log4j2
@AllArgsConstructor
@Service
public class RoleServiceImpl  implements  IRoleService{
    private final RoleRepository repository;
    private final IRoleMapper roleMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Page<RoleResponseRecord> findAllWithPagination(int page, int size, String orderBy, String order) {
        log.info("RoleServiceImpl call method findAllWithPagination()");
        Sort.Direction direction = order.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Page<RoleEntity> data = this.repository.findAll(PageRequest.of(page, size, Sort.by(direction, orderBy)));
        return data.map(this.roleMapper::roleEntityToRoleResponseRecord);
    }

    @Override
    public List<RoleResponseRecord> findAll() {
        log.info("RoleServiceImpl call method findAll()");
        List<RoleEntity> data = this.repository.findAll();
        return data.stream().map(this.roleMapper::roleEntityToRoleResponseRecord).toList();
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

    @Override
    public TotalUserByRolResponseRecord findTotalRoles() {
        log.info("RoleServiceImpl call method getTotalRolesByRoleId()");
        String query = """
                    select r.name , r.description ,COUNT(r.id) as total from roles r
                    inner join users_roles ur on ur.role_id  = r.id
                    group by r.id
                """;
        List<TotalUserByRolQueryResponseRecord> users = this.jdbcTemplate.query(query, (rs, row) -> new TotalUserByRolQueryResponseRecord(
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("total")
        ));
        List<String> labelRoles = new ArrayList<>();
        List<Integer> total = new ArrayList<>();
        users.forEach( u ->{
            labelRoles.add(u.description());
            total.add((u.total()));
            log.info("NAME ROLE: "+ u.name() + "  DESCRIPTION: " + u.description() + " TOTAL:" + u.total());
        });
        log.info("LABELS: " + labelRoles);
        log.info("SERIES: " + total);
        return  new TotalUserByRolResponseRecord(labelRoles, total);
    }
}
