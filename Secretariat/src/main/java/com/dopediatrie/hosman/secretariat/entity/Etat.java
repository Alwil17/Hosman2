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
public class Etat {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String nom;
    private String slug;
    private String couleur;
    private int indice;
    @OneToMany(mappedBy = "etat")
    @JsonIgnore
    private List<Facture> factures;
    @OneToMany(mappedBy = "etat")
    @JsonIgnore
    private List<Creance> creances;
    @OneToMany(mappedBy = "etat")
    @JsonIgnore
    private List<Reliquat> reliquats;
    private long structure_id;
}