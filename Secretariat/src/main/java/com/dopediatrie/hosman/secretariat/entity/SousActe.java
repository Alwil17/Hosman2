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
public class SousActe {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String libelle;
    private String slug;
    private String code;
    @ManyToOne
    @JoinColumn(name = "acte_id")
    private Acte acte;
    @OneToMany(mappedBy = "sous_acte")
    @JsonIgnore
    private List<Tarif> tarifs;


}