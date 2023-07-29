package com.dopediatrie.hosman.secretariat.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {
    private long id;
    private String reference;
    private String nom;
    private String prenoms;
    private LocalDateTime date_naissance;
    private char sexe;
    private String lieu_naissance;
    private String tel1;
    private String tel2;
    private String email;
    private String type_piece;
    private String no_piece;
    private boolean is_assure;
    private long pays_origine_id;
    private long profession_id;
    private long employeur_id;
    private long structure_id;
}
