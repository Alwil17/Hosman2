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
public class Coefficient {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private double nbe = 0;
    private double nim = 0;
    private double nip = 0;
    private double smf = 0;
    private double mni = 0;
    private double mf = 0;
    private double pf = 0;
    private double ass = 0;
    private double imv = 0;

    private double coef = 0;
    private String commentaire;

    @OneToOne(mappedBy = "coefficient")
    @JsonIgnore
    private Patient patient;
}