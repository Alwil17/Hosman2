package com.dopediatrie.hosman.secretariat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient implements Serializable {
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
    @JoinColumn(name = "nationalite_id")
    private Pays nationalite;
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
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<RendezVous> rdvs;
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<PrestationTemp> temp_prestations;
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<PEC> pecs;
    @OneToOne
    @JoinColumn(name = "adresse_id")
    private Adresse adresse;
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Creance> creances;
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Majoration> majorations;
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Reduction> reductions;
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Reliquat> reliquats;
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Attente> attentes;
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Filiation> parents;
    @ManyToOne
    @JoinColumn(name = "assurance_id")
    private Assurance assurance;
    private double taux_assurance;
    private Date date_debut_assurance;
    private Date date_fin_assurance;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "maladie_patient",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "maladie_id", referencedColumnName = "id"))
    private List<Maladie> maladies;
    private String commentaire;
    private String antecedent;

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
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
                ", is_assure=" + is_assure +
                ", date_ajout=" + date_ajout +
                ", pays_origine=" + pays_origine +
                ", nationalite=" + nationalite +
                ", profession=" + profession +
                ", employeur=" + employeur +
                ", personne_a_prevenir=" + personne_a_prevenir +
                ", structure_id=" + structure_id +
                ", adresse=" + adresse +
                ", assurance=" + assurance +
                ", taux_assurance=" + taux_assurance +
                ", date_debut_assurance=" + date_debut_assurance +
                ", date_fin_assurance=" + date_fin_assurance +
                '}';
    }
}
