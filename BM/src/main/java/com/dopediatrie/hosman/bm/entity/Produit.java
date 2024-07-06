package com.dopediatrie.hosman.bm.entity;

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
public class Produit {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String nom;
    private String dci;
    private String autre;
    private String infos;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "produit_classe",
            joinColumns = @JoinColumn(name = "produit_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "classe_id", referencedColumnName = "id"))
    private List<Classe> classes;
    @OneToMany(mappedBy = "produit")
    private List<Indication> indications;
    @OneToMany(mappedBy = "produit")
    private List<ContreIndication> contre_indications;
    @OneToMany(mappedBy = "produit")
    private List<EffetSecondaire> effet_secondaires;
    @OneToMany(mappedBy = "produit")
    private List<Posologie> posologies;
    @OneToMany(mappedBy = "produit")
    private List<Forme> formes;
    @OneToMany(mappedBy = "produit")
    private List<Notif> notifs;
    @ManyToOne
    @JoinColumn(name = "agence_id")
    private Agence agence;
    @ManyToOne
    @JoinColumn(name = "laboratoire_id")
    private Laboratoire laboratoire;
    @ManyToOne
    @JoinColumn(name = "delegue_id")
    private Delegue delegue;
    @OneToMany(mappedBy = "produit")
    @JsonIgnore
    private List<Prescription> prescriptions;
}
