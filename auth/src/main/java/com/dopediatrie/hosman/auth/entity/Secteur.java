package com.dopediatrie.hosman.auth.entity;

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
    private String code;
    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;
    @OneToMany(mappedBy = "secteur")
    @JsonIgnore
    private List<Employe> employes;
}