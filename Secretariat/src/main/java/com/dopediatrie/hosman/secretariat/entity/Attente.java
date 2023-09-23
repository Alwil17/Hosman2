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
public class Attente {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private long num_attente;
    private int ordre = 0;
    private boolean attente = true;
    private LocalDateTime date_attente;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "medecin")
    private Medecin medecin;
    @ManyToOne
    @JoinColumn(name = "receveur_id")
    private Medecin receveur;
    @ManyToOne
    @JoinColumn(name = "secteur_id")
    private Secteur secteur;
    @OneToOne
    @JoinColumn(name = "facture_id")
    private Facture facture;
    private long structure_id;

    @Override
    public String toString() {
        return "Attente{" +
                "id=" + id +
                ", num_attente=" + num_attente +
                ", ordre=" + ordre +
                ", attente=" + attente +
                ", date_attente=" + date_attente +
                ", structure_id=" + structure_id +
                '}';
    }
}