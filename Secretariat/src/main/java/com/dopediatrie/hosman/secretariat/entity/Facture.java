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
public class Facture {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String reference;
    private double total;
    private double montant_pec;
    @OneToOne
    @JoinColumn(name = "majoration_id")
    private Majoration majoration;
    @OneToOne
    @JoinColumn(name = "reduction_id")
    private Reduction reduction;
    private double a_payer;
    @OneToOne
    @JoinColumn(name = "reliquat_id")
    private Reliquat reliquat;
    @OneToOne
    @JoinColumn(name = "creance_id")
    private Creance creance;
    private LocalDateTime date_facture;
    private LocalDateTime date_reglement;
    @ManyToOne
    @JoinColumn(name = "etat_id")
    private Etat etat;
    private int exporte;
    @OneToOne
    @JoinColumn(name = "prestation_id")
    private Prestation prestation;
    @OneToOne(mappedBy = "facture")
    @JsonIgnore
    private Attente attente;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "mode_facture",
            joinColumns = @JoinColumn(name = "facture_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "mode_payement_id", referencedColumnName = "id"))
    private List<ModePayement> mode_payements;

    @Override
    public String toString() {
        return "Facture{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", total=" + total +
                ", montant_pec=" + montant_pec +
                ", a_payer=" + a_payer +
                ", date_facture=" + date_facture +
                ", date_reglement=" + date_reglement +
                '}';
    }
}
