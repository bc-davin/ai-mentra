package com.app.AppApplication.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "user_mail", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "profile_picture_url", nullable = false)
    private String profilePictureUrl;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_school", nullable = false)
    private String userSchool;

    @Column(name = "is_enabled", nullable = false)
    private boolean enabled;

    @Column(name = "is_removed", nullable = false)
    private boolean removed;

    @Column(name = "is_admin", nullable = false)
    private boolean admin;

    @Column(name = "is_temp_password")
    private Boolean tempPassword;

    @Column(name = "m_datetime", nullable = false)
    private LocalDateTime mDatetime;

    @Column(name = "r_datetime", nullable = false)
    private LocalDateTime rDatetime;
}

