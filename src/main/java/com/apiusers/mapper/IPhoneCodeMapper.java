package com.apiusers.mapper;

import com.apiusers.entity.PhoneCodeEntity;
import com.apiusers.record.response.PhoneCodeResponseRecord;
import org.mapstruct.Mapper;

@Mapper
public interface IPhoneCodeMapper {
    PhoneCodeResponseRecord userEntityToUserResponseRecord(PhoneCodeEntity phoneCodeEntity);

}
