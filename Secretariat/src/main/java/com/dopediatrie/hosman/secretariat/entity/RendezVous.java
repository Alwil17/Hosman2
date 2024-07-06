package com.dopediatrie.hosman.secretariat.entity;

import com.dopediatrie.hosman.secretariat.payload.response.MedecinResponse;
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
public class RendezVous {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String medecin_ref;
    @Transient
    private MedecinResponse medecin;
    private String intervenant_ref;
    @Transient
    private MedecinResponse intervenant;
    private String patient_nom;
    private String patient_prenoms;
    private char patient_sexe;
    private LocalDateTime patient_naiss;
    @ManyToOne
    @JoinColumn(name = "etat_id")
    private Etat etat;
    private LocalDateTime date_rdv;
    private String objet;
}