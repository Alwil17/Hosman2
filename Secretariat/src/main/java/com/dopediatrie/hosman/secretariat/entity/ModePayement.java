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
public class ModePayement {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String nom;
    @ManyToMany(mappedBy = "mode_payements")
    @JsonIgnore
    private List<Encaissement> encaissements;
    @ManyToMany(mappedBy = "mode_payements")
    @JsonIgnore
    private List<Facture> factures;
}