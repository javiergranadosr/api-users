package com.apiusers.mapper;

import com.apiusers.entity.PhoneCodeEntity;
import com.apiusers.record.response.PhoneCodeResponseRecord;
import org.springframework.stereotype.Component;

@Component
public class PhoneCodeMapperImpl implements IPhoneCodeMapper{
    @Override
    public PhoneCodeResponseRecord userEntityToUserResponseRecord(PhoneCodeEntity phoneCodeEntity) {
        if (phoneCodeEntity == null) {return null;}
        return new PhoneCodeResponseRecord(phoneCodeEntity.getId(), phoneCodeEntity.getCountryName(), phoneCodeEntity.getCountryCode(), phoneCodeEntity.getCreatedAt(), phoneCodeEntity.getUpdatedAt());
    }
}
