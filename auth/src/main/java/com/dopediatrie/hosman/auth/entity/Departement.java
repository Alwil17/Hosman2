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
public class Departement {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String nom;
    private String slug;
    private String code;
    @ManyToOne
    @JoinColumn(name = "structure_id")
    private Structure structure;
    @OneToMany(mappedBy = "departement")
    @JsonIgnore
    private List<Poste> postes;
    @OneToMany(mappedBy = "departement")
    @JsonIgnore
    private List<Employe> employes;
}