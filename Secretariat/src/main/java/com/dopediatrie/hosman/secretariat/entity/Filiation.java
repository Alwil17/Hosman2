package com.dopediatrie.hosman.secretariat.entity;

import com.dopediatrie.hosman.secretariat.payload.response.MedecinResponse;
import com.dopediatrie.hosman.secretariat.payload.response.SecteurResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Filiation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String nom;
    private String prenoms;
    private String telephone;
    private char sexe;
    private String type;
    private int annee_naissance;
    @ManyToOne
    @JoinColumn(name = "profession_id")
    private Profession profession;
    @ManyToOne
    @JoinColumn(name = "employeur_id")
    private Employeur employeur;
    private int assurance;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;

    @Override
    public String toString() {
        return "Filiation{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenoms='" + prenoms + '\'' +
                ", telephone='" + telephone + '\'' +
                ", sexe=" + sexe +
                ", type='" + type + '\'' +
                ", annee_naissance=" + annee_naissance +
                '}';
    }
}