package com.apiusers.repository;

import com.apiusers.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    @Query(nativeQuery = true, value = """
            select
            u.id, 
            u.name,
            u.lastname,
            u.username, 
            u.telephone,
            u.email,
            u.created_at,
            u.updated_at,
            u.phone_code_id,
            r.id as role_id,
            r.name as role_name,
            r.description as role_description,
            r.created_at as role_created_at,
            r.updated_at as role_updated_at 
            from users  u
            left join users_roles ur on ur.user_id = u.id
            left join roles r on r.id  = ur.role_id 
            where r.id = :roleId
            """)
    List<UserEntity> findAllUsersByRoleId(@Param("roleId") Long roleId);
}
