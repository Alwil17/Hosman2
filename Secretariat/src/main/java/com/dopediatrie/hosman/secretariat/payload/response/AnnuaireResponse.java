package com.dopediatrie.hosman.secretariat.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnnuaireResponse {
    private long id;
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
    private String categorie_slug;
    private NameResponse categorie;

    public AnnuaireResponse(long id, String nom, String prenom, String profession, String secteur, String bureau, String tel1, String tel2, String domicile, String email, String bip, String no_poste, String categorie_slug) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.profession = profession;
        this.secteur = secteur;
        this.bureau = bureau;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.domicile = domicile;
        this.email = email;
        this.bip = bip;
        this.no_poste = no_poste;
        this.categorie_slug = categorie_slug;
    }

    public AnnuaireResponse(long id, String nom, String secteur, String bureau, String bip, String no_poste) {
        this.id = id;
        this.nom = nom;
        this.secteur = secteur;
        this.bureau = bureau;
        this.bip = bip;
        this.no_poste = no_poste;
    }
}
