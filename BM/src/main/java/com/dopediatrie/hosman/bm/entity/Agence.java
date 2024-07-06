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
public class Agence {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String nom;
    private String slug;
    private String directeur;
    private String email;
    private String tel1;
    private String tel2;
    private String adresse;
    @OneToMany(mappedBy = "agence")
    @JsonIgnore
    private List<Laboratoire> laboratoires;
    @OneToMany(mappedBy = "agence")
    @JsonIgnore
    private List<Produit> produits;
}
