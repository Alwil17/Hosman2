package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String reference;
    private String nom;
    private String prenoms;
    private LocalDateTime date_naissance;
    private char sexe;
    private String lieu_naissance;
    private String tel1;
    private String tel2;
    private String email;
    private String type_piece;
    private String no_piece;
    private int is_assure;
    @ManyToOne
    @JoinColumn(name = "pays_origine_id")
    private Pays pays_origine;
    @ManyToOne
    @JoinColumn(name = "profession_id")
    private Profession profession;
    @ManyToOne
    @JoinColumn(name = "employeur_id")
    private Employeur employeur;
    private long structure_id;
}
