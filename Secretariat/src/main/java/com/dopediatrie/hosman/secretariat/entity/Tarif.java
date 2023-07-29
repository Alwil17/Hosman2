package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tarif {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String designation;
    private double tarif_non_assure;
    private double tarif_etr_non_assure;
    private double tarif_assur_locale;
    private double tarif_assur_etr;
    private double tarif_assur_hors_zone;
    private long structure_id;
    private long sous_acte_id;
    private String description;
}
