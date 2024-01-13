package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnnuaireRequest {
    private String nom;
    private String prenom;
    private String profession;
    private String secteur;
    private String bureau;
    private String tel1;
    private String tel2;
    private String domicile;
    private String email;
    private String bip;
    private String no_poste;
    private String categorie;
}