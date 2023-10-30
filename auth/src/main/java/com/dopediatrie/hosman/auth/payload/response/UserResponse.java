package com.dopediatrie.hosman.auth.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private long id;
    private String username;
    private String password;
    private String avatar;
    private String remember_token;
    private boolean is_active;
    private LocalDateTime last_access_time;
    private LocalDateTime email_verified_at;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
