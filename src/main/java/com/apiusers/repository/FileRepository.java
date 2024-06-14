package com.apiusers.repository;

import com.apiusers.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface FileRepository extends JpaRepository<FileEntity, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM files WHERE user_id = ?1")
    Optional<FileEntity> findByUserId(Long userId);
}
