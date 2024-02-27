package com.apiusers.service;

import com.apiusers.record.request.UserRequestRecord;
import com.apiusers.record.response.ResponseMessageRecord;
import com.apiusers.record.response.UserResponseRecord;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService {
    Page<UserResponseRecord> findAllWithPagination(int page, int size, String orderBy, String order);
    List<UserResponseRecord> findAll();
    UserResponseRecord findById(Long id);
    ResponseMessageRecord create(UserRequestRecord data);
    ResponseMessageRecord update(Long id, UserRequestRecord data);
    ResponseMessageRecord delete(Long id);
}

