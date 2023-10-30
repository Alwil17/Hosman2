package com.dopediatrie.hosman.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Structure {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String nom;
    private String raison_sociale;
    private String slug;
    private String email;
    private String adresse;
    private String adresse2;
    private String adresse3;
    private String tel1;
    private String tel2;
    private String tel3;
    private String bp;
    private String fax;
    private String logo;
    private String baniere;
    private String slogan;
    private String sigle;
    private String cnss;
    private String rib;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    @OneToMany(mappedBy = "structure")
    @JsonIgnore
    private List<Departement> departements;
    @OneToMany(mappedBy = "structure")
    @JsonIgnore
    private List<Employe> employes;
}