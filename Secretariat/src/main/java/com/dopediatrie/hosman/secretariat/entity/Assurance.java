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
public class Assurance {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String nom;
    private String representant;
    private String email;
    private String tel1;
    private String tel2;
    @ManyToOne
    @JoinColumn(name = "type_assurance_id")
    private TypeAssurance type_assurance;
    @ManyToMany(mappedBy = "assurances")
    private List<Patient> patients;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "assurance_tarif",
            joinColumns = @JoinColumn(name = "assurance_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tarif_id", referencedColumnName = "id"))
    private List<Tarif> tarifs;
}
