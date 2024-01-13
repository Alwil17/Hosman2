package com.dopediatrie.hosman.secretariat.entity;

import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Annuaire {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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
    @Transient
    private NameResponse categorie;
}