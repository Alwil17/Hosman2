package com.dopediatrie.hosman.secretariat.entity;

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
public class Prestation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    private String provenance;
    private String demandeur;
    private String consulteur;
    private String secteur_code;
    private LocalDateTime date_prestation;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "prestation_tarif",
            joinColumns = @JoinColumn(name = "prestation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tarif_id", referencedColumnName = "id"))
    private List<Tarif> tarifs;
    @OneToOne(mappedBy = "prestation")
    @JsonIgnore
    private Facture facture;

    @Override
    public String toString() {
        return "Prestation{" +
                "id=" + id +
                ", provenance=" + provenance +
                ", date_prestation=" + date_prestation +
                '}';
    }
}
