package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class InterventionTarif {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private long intervention_id;
    private long tarif_id;
}
