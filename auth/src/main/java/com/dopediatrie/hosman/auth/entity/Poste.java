package com.dopediatrie.hosman.auth.entity;

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
public class Poste {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String intitule;
    private String slug;
    private String code;
    @ManyToMany(mappedBy = "postes")
    @JsonIgnore
    private List<Employe> employes;
}