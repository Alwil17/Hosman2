package com.dopediatrie.hosman.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employe {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String matricule;
    private String nom;
    private String prenoms;
    private char sexe;
    private String tel1;
    private String tel2;
    private String email;
    private String adresse;
    private String localisation;
    private String autres;
    private LocalDateTime date_debut;
    private LocalDateTime date_fin;
    private boolean is_employe;
    private boolean is_temporaire;
    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;
    private long profession_id;
    private long nationalite_id;
    @ManyToOne
    @JoinColumn(name = "structure_id")
    private Structure structure;
    @OneToOne(mappedBy = "employe")
    @JsonIgnore
    private User user;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employe_poste",
            joinColumns = @JoinColumn(name = "employe_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "poste_id", referencedColumnName = "id"))
    @JsonIgnore
    private List<Poste> postes;
}