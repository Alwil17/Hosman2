package com.dopediatrie.hosman.hospi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LitResponse {
    private long id;
    private String nom;
    private String slug;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
