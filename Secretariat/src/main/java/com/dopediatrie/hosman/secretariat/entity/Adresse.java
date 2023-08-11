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
public class Adresse {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String arrondissement;
    private String no_maison;
    private String rue;
    private String bp;
    @ManyToOne
    @JoinColumn(name = "ville_id")
    private Ville ville;
    @ManyToOne
    @JoinColumn(name = "quartier_id")
    private Quartier quartier;
    @ManyToMany(mappedBy = "adresses")
    @JsonIgnore
    private List<Patient> patients;
}
