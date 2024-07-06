package com.dopediatrie.hosman.bm.entity;

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
public class Forme {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String presentation;
    private String dosage;
    private String conditionnement;
    private double prix = 0;
    @ManyToOne
    @JoinColumn(name = "produit_id")
    @JsonIgnore
    private Produit produit;
    @OneToMany(mappedBy = "forme")
    @JsonIgnore
    private List<Prescription> prescriptions;
}
