package com.dopediatrie.hosman.secretariat.entity;

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
public class Patient {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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
    private int is_assure;
    private LocalDateTime date_ajout;
    @ManyToOne
    @JoinColumn(name = "pays_origine_id")
    private Pays pays_origine;
    @ManyToOne
    @JoinColumn(name = "profession_id")
    private Profession profession;
    @ManyToOne
    @JoinColumn(name = "employeur_id")
    private Employeur employeur;
    @OneToOne
    @JoinColumn(name = "personne_a_prevenir_id")
    private PersonneAPrevenir personne_a_prevenir;
    private long structure_id;
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Prestation> prestations;
    @OneToOne
    @JoinColumn(name = "adresse_id")
    private Adresse adresse;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "patient_assurance",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "assurance_id", referencedColumnName = "id"))
    private List<Assurance> assurances;
}
