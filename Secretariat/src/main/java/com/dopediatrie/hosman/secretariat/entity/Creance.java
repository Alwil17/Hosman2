package com.dopediatrie.hosman.secretariat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class Creance {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private double montant;
    private LocalDateTime date_operation;
    private LocalDateTime date_reglement;
    @OneToOne(mappedBy = "creance")
    @JsonIgnore
    private Facture facture;
    @ManyToOne
    @JoinColumn(name = "etat_id")
    private Etat etat;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;

    @Override
    public String toString() {
        return "Creance{" +
                "id=" + id +
                ", montant=" + montant +
                ", date_operation='" + date_operation + '\'' +
                '}';
    }
}