package com.dopediatrie.hosman.bm.entity;

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
public class Constante {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private double poids;
    private double taille;
    private String tension;
    private double temperature;
    private double poul;
    private double perimetre_cranien;
    @OneToOne(mappedBy = "constante")
    @JsonIgnore
    private Consultation consultation;
}
