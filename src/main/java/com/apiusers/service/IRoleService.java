package com.apiusers.service;

import com.apiusers.record.request.RoleRequestRecord;
import com.apiusers.record.response.ResponseMessageRecord;
import com.apiusers.record.response.RoleResponseRecord;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRoleService {

    Page<RoleResponseRecord> findAllWithPagination(int page, int size, String orderBy, String order);
    List<RoleResponseRecord> findAll();
    RoleResponseRecord findById(Long id);
    ResponseMessageRecord create(RoleRequestRecord data);
    ResponseMessageRecord update(Long id, RoleRequestRecord data);
    ResponseMessageRecord delete(Long id);
}
