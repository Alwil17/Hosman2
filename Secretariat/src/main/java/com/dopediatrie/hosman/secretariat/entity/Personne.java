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
public class Personne {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String nom;
    private String prenoms;
    private String tel1;
    private String tel2;
    private String type_piece;
    private String no_piece;
    private String adresse;
    @OneToMany(mappedBy = "beneficiaire")
    @JsonIgnore
    private List<Depense> depenses;
}
