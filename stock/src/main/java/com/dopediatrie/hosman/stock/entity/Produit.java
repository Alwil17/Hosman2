package com.dopediatrie.hosman.stock.entity;

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
public class Produit {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String code;
    private String nom;
    private String nom_officiel;
    private String dci;
    private String code_acte;
    private String autre;
    private double prix;

}
