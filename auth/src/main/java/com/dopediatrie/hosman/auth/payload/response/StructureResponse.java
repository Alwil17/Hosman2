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
public class StructureResponse {
    private long id;
    private String nom;
    private String raison_sociale;
    private String slug;
    private String email;
    private String adresse;
    private String adresse2;
    private String adresse3;
    private String tel1;
    private String tel2;
    private String tel3;
    private String bp;
    private String fax;
    private String logo;
    private String baniere;
    private String slogan;
    private String sigle;
    private String cnss;
    private String rib;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
