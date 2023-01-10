package com.barbozaguiii.comissiondealauth.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Entity(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserModel {

    @Id
    @Length(min = 36, max = 36)
    private String id;

    @Column
    private String login;

    @Column
    private String userPassword;

    @Column
    @Email
    private String email;

    @Column(name = "expired_account")
    private Boolean accountExpired;

    @Column(name = "locked_account")
    private Boolean accountLocked;

    @Column(name = "credentials_expired")
    private Boolean credentialsExpired;

    @Column(name = "enabled_user")
    private Boolean enabledUser;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;



    @PrePersist
    private void generateId() {
        setId(UUID.randomUUID().toString());
        setEnabledUser(true);
        setAccountExpired(false);
        setAccountLocked(false);
        setCredentialsExpired(false);
    }
}
