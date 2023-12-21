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
    @Transient
    private MedecinResponse medecin_consulteur;
    private String medecin;
    @Transient
    private MedecinResponse medecin_receveur;
    private String receveur;
    @Transient
    private SecteurResponse secteur;
    private String secteur_code;
    @OneToOne
    @JoinColumn(name = "facture_id")
    private Facture facture;
    private boolean urgence = false;
    private boolean en_cours = false;
    private long structure_id;

    @Override
    public String toString() {
        return "Attente{" +
                "id=" + id +
                ", num_attente=" + num_attente +
                ", ordre=" + ordre +
                ", attente=" + attente +
                ", date_attente=" + date_attente +
                ", urgence=" + urgence +
                ", structure_id=" + structure_id +
                '}';
    }
}