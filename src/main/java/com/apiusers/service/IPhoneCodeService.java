package com.apiusers.service;

import com.apiusers.record.response.PhoneCodeResponseRecord;

import java.util.List;

public interface IPhoneCodeService {
    List<PhoneCodeResponseRecord> findAll();
}
