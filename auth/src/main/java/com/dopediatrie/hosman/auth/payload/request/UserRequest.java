package com.dopediatrie.hosman.auth.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserRequest {
    private String username;
    private String password;
    private String avatar;
    private String remember_token;
    private boolean is_active;
    private LocalDateTime last_access_time;
    private LocalDateTime email_verified_at;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String employe_matricule;
}
