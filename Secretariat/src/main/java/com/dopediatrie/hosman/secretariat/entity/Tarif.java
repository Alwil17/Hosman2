package com.dopediatrie.hosman.secretariat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @ManyToOne
    @JoinColumn(name = "sous_acte_id")
    private SousActe sous_acte;
    private String description;
    @ManyToMany(mappedBy = "tarifs")
    @JsonIgnore
    private List<Assurance> assurances;
    @ManyToMany(mappedBy = "tarifs")
    @JsonIgnore
    private List<Intervention> interventions;
}
