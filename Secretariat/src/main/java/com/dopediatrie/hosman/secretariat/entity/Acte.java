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
public class Acte {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String libelle;
    private String slug;
    private String code;
    private int position = 0;
    private long structure_id;
    @ManyToOne
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;
    @OneToMany(mappedBy = "acte")
    @JsonIgnore
    private List<Tarif> tarifs;
}