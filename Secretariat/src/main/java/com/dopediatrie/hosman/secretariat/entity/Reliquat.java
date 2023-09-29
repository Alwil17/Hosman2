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
public class Reliquat {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private double montant;
    private LocalDateTime date_operation;
    private LocalDateTime date_retrait;
    @Transient
    private String facture_ref;
    @OneToOne(mappedBy = "reliquat")
    @JsonIgnore
    private Facture facture;
    @ManyToOne
    @JoinColumn(name = "etat_id")
    private Etat etat;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Override
    public String toString() {
        return "Reliquat{" +
                "id=" + id +
                ", montant=" + montant +
                ", date_operation='" + date_operation + '\'' +
                '}';
    }
}