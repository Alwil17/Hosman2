package com.dopediatrie.hosman.secretariat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @OneToOne(mappedBy = "majoration")
    @JsonIgnore
    private Facture facture;

    @Override
    public String toString() {
        return "Majoration{" +
                "id=" + id +
                ", montant=" + montant +
                ", motif='" + motif + '\'' +
                '}';
    }
}