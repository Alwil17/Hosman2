package com.dopediatrie.hosman.bm.entity;

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
public class Intervention {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String reference;
    private LocalDateTime date_intervention;
    private String type;
    private String commentaire;
    private String hdm;
    private long patient_id;
    private long secteur_id;
    private long attente_id;
    @OneToOne
    @JoinColumn(name = "constante_id")
    private Constante constante;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "motif_intervention",
            joinColumns = @JoinColumn(name = "intervention_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "motif_id", referencedColumnName = "id"))
    private List<Motif> motifs;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "diagnostic_intervention",
            joinColumns = @JoinColumn(name = "intervention_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "diagnostic_id", referencedColumnName = "id"))
    private List<Diagnostic> diagnostics;
}
