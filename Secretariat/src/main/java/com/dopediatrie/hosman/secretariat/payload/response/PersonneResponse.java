package com.dopediatrie.hosman.secretariat.payload.response;

import com.dopediatrie.hosman.secretariat.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonneResponse {
    private long id;
    private String nom;
    private String prenoms;
    private String tel1;
    private String tel2;
    private String type_piece;
    private String no_piece;
    private String adresse;
}
