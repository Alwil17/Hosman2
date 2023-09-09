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
public class Majoration {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private double montant;
    private String motif;
    private LocalDateTime date_operation;
    @OneToOne(mappedBy = "majoration")
    @JsonIgnore
    private Facture facture;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Override
    public String toString() {
        return "Majoration{" +
                "id=" + id +
                ", montant=" + montant +
                ", motif='" + motif +
                ", date_operation='" + date_operation + '\'' +
                '}';
    }
}