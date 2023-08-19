package com.dopediatrie.hosman.secretariat.entity;

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
public class Secteur {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String libelle;
    private String slug;
    private String couleur;
    private String code; //lettre dans le diagramme
    @OneToMany(mappedBy = "secteur")
    @JsonIgnore
    private List<Medecin> medecins;
    @OneToMany(mappedBy = "secteur")
    @JsonIgnore
    private List<Prestation> prestations;
}