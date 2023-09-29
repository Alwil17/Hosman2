package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "mode_creance")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreanceMode {
    @EmbeddedId
    private CreanceModePK id;

    @ManyToOne
    @MapsId("creance_id")
    @JoinColumn(name = "creance_id")
    private Creance creance;
    @ManyToOne
    @MapsId("mode_payement_id")
    @JoinColumn(name = "mode_payement_id")
    private ModePayement mode_payement;
    private double montant;
    private LocalDateTime date_depot;
}
