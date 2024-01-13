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
public class Consultation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String reference;
    private LocalDateTime date_consultation;
    private String type;
    private String commentaire;
    private String hdm;
    private String patient_ref;
    private String secteur_code;
    private Long attente_num;
    @OneToOne
    @JoinColumn(name = "constante_id")
    private Constante constante;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "motif_consultation",
            joinColumns = @JoinColumn(name = "consultation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "motif_id", referencedColumnName = "id"))
    private List<Motif> motifs;
    @OneToMany(mappedBy = "consultation")
    @JsonIgnore
    private List<ConsultationDiagnostic> diagnostics;
    @OneToMany(mappedBy = "consultation")
    @JsonIgnore
    private List<ConsultationActe> actes;

}
