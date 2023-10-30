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
public class Laboratoire {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String nom;
    private String slug;
    private String tel1;
    private String tel2;
    private String email;
    private String adresse;
    @ManyToOne
    @JoinColumn(name = "agence_id")
    private Agence agence;
    @OneToMany(mappedBy = "laboratoire")
    @JsonIgnore
    private List<Delegue> delegues;
    @OneToMany(mappedBy = "laboratoire")
    @JsonIgnore
    private List<Produit> produits;
}
