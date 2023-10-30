package com.dopediatrie.hosman.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String username;
    private String password;
    private String avatar;
    private String remember_token;
    private boolean is_active = true;
    private LocalDateTime last_access_time;
    private LocalDateTime email_verified_at;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    @OneToOne
    @JoinColumn(name = "employe_id")
    private Employe employe;
}