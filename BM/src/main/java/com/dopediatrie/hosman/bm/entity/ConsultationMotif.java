package com.dopediatrie.hosman.bm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "motif_consultation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultationMotif {
    @EmbeddedId
    private ConsultationMotifPK id;

    @ManyToOne
    @MapsId("consultation_id")
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;
    @ManyToOne
    @MapsId("motif_id")
    @JoinColumn(name = "motif_id")
    private Motif motif;
    private String caractere;
}
