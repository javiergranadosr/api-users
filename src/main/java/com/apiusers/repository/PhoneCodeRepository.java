package com.apiusers.repository;

import com.apiusers.entity.PhoneCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneCodeRepository extends JpaRepository<PhoneCodeEntity, Long> {
}
