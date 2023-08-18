package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MedecinRequest {
    private String reference = "";
    private String nom;
    private String prenoms;
    private LocalDateTime date_naissance = LocalDateTime.now();
    private char sexe = 'N';
    private String lieu_naissance = "";
    private String tel1 = "";
    private String tel2 = "";
    private String email = "";
    private String type_piece = "CNI";
    private String no_piece = "";
    private String type = "interne";
    private long employeur_id;
    private long secteur_id;
}