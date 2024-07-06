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
public class Antecedant {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String type;
    private boolean has_diabete;
    private String type_diabete;
    private boolean has_ugd;
    private boolean has_hta;
    private boolean has_asthme;
    private boolean has_drepano;
    private String type_drepano;
    private boolean has_alcool;
    private boolean has_tabac;
    @Column(name="nb_tabac", columnDefinition="int(11) default '0'")
    private int nb_tabac;
    private String mesure_tabac;
    private String frequence_tabac;
    @Column(name="nb_medic", columnDefinition="int(11) default '0'")
    private int nb_medic;
    @Lob
    private String medics;
    @Transient
    private List<String> medicaments;
    @Column(name="nb_chirurgie", columnDefinition="int(11) default '0'")
    private int nb_chirurgie;
    @Lob
    private String chirurs;
    @Transient
    private List<String> chirurgies;
    @Column(name="nb_hospit", columnDefinition="int(11) default '0'")
    private int nb_hospit;
    @Lob
    private String hospits;
    @Transient
    private List<String> hospitalisations;
    private String allergies;
    private String autre;
    //enfant
    private String voie_accouch;
    private String voie_cause;
    private boolean reanime;
    private boolean scolarise;
    private String classe_scolarise;

    @OneToOne(mappedBy = "antecedant")
    @JsonIgnore
    private Patient patient;
}