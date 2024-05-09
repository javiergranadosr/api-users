package com.apiusers.service;

import com.apiusers.entity.PhoneCodeEntity;
import com.apiusers.mapper.IPhoneCodeMapper;
import com.apiusers.record.response.PhoneCodeResponseRecord;
import com.apiusers.repository.PhoneCodeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@AllArgsConstructor
@Service
public class PhoneCodeServiceImpl implements IPhoneCodeService{

    private final PhoneCodeRepository repository;
    private final IPhoneCodeMapper mapper;

    @Override
    public List<PhoneCodeResponseRecord> findAll() {
        List<PhoneCodeEntity> codes = this.repository.findAll();
        return codes.stream().map(this.mapper::userEntityToUserResponseRecord).toList();
    }
}
