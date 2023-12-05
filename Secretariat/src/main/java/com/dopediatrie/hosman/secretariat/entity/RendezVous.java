package com.dopediatrie.hosman.secretariat.entity;

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
    private String medecin;
    private String intervenant;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "etat_id")
    private Etat etat;
    private LocalDateTime date_rdv;
    private String objet;
}