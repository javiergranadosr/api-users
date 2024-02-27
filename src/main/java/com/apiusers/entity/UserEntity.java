package com.apiusers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Crea tabla users_roles con los campos de user_id, role_id
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","role_id"})} // Datos unicos
    )
    private Set<RoleEntity> roles;

    @Column(name = "name", nullable = false, length = 200)
    private String name;
    @Column(name = "lastname", nullable = false, length = 200)
    private String lastname;
    @Column(name = "username", nullable = false, length = 30, unique = true)
    private String username;
    @Column(name = "email", nullable = false, length = 255)
    private String email;
    @Column(name = "telephone", nullable = false, length = 20)
    private String telephone;

    @CreationTimestamp
    private LocalDate createdAt;
    @UpdateTimestamp
    private LocalDate updatedAt;
}
