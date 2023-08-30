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
public class Reliquat {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private double montant;
    @OneToOne(mappedBy = "reliquat")
    @JsonIgnore
    private Facture facture;
    @ManyToOne
    @JoinColumn(name = "etat_id")
    private Etat etat;
}