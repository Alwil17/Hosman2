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
public class Medecin {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
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
    private String type;
    @ManyToOne
    @JoinColumn(name = "employeur_id")
    private Employeur employeur;
    @ManyToOne
    @JoinColumn(name = "secteur_id")
    private Secteur secteur;
    @OneToMany(mappedBy = "demandeur")
    @JsonIgnore
    private List<Prestation> prestations_demandeur;
    @OneToMany(mappedBy = "medecin")
    @JsonIgnore
    private List<Attente> attentes;
    @OneToMany(mappedBy = "receveur")
    @JsonIgnore
    private List<Attente> recu_attentes;
    @OneToMany(mappedBy = "consulteur")
    @JsonIgnore
    private List<Prestation> prestations_consulteur;
    @OneToMany(mappedBy = "demandeur")
    @JsonIgnore
    private List<PrestationTemp> temp_prestations_demandeur;
    @OneToMany(mappedBy = "consulteur")
    @JsonIgnore
    private List<PrestationTemp> temp_prestations_consulteur;

    @Override
    public String toString() {
        return "Medecin{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenoms='" + prenoms + '\'' +
                ", date_naissance=" + date_naissance +
                ", sexe=" + sexe +
                ", lieu_naissance='" + lieu_naissance + '\'' +
                ", tel1='" + tel1 + '\'' +
                ", tel2='" + tel2 + '\'' +
                ", email='" + email + '\'' +
                ", type_piece='" + type_piece + '\'' +
                ", no_piece='" + no_piece + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
