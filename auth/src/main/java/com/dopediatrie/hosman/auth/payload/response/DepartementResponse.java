package com.dopediatrie.hosman.auth.payload.response;

import com.dopediatrie.hosman.auth.entity.Structure;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartementResponse {
    private long id;
    private String nom;
    private String slug;
    private String code;
}
