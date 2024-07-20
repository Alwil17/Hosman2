package com.dopediatrie.hosman.bm.entity;

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
public class Prescription {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String presentation;
    private int qte = 0;
    private String conditionnement;
    private int dose_qte;
    private String dose;
    private String periode;
    private String adverbe;
    private int duree_qte;
    private String duree;
    @Lob
    private String note;
    private String heures;
    private boolean mu;
    private String mu_groupe;
    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;
    @ManyToOne
    @JoinColumn(name = "forme_id")
    private Forme forme;
    @ManyToMany(mappedBy = "prescriptions")
    @JsonIgnore
    private List<Ordonnance> ordonnances;
}
