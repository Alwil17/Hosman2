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
    private String libelle;
    private String slug;
    private String code;
    private String description;
    private double tarif_non_assure = 0;
    private double tarif_etr_non_assure = 0;
    private double tarif_assur_locale = 0;
    private double tarif_assur_etr = 0;
    private double tarif_assur_hors_zone = 0;
    private long structure_id;
    @ManyToOne
    @JoinColumn(name = "acte_id")
    private Acte acte;
    @ManyToMany(mappedBy = "tarifs")
    @JsonIgnore
    private List<Assurance> assurances;
    @ManyToMany(mappedBy = "tarifs")
    @JsonIgnore
    private List<Prestation> prestations;
    @ManyToMany(mappedBy = "tarifs")
    @JsonIgnore
    private List<PrestationTemp> temp_prestations;
    @OneToMany(mappedBy = "tarif")
    @JsonIgnore
    private List<PEC> pecs;
}
