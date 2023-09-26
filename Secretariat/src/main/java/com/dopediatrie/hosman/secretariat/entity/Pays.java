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
public class Pays {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String nom;
    private String slug;
    private String nationalite;
    private String code;
    private String indicatif;
    @OneToMany(mappedBy = "pays_origine")
    @JsonIgnore
    private List<Patient> patients_originaires;
    @OneToMany(mappedBy = "nationalite")
    @JsonIgnore
    private List<Patient> patients_nationalites;

    @Override
    public String toString() {
        return "Pays{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", slug='" + slug + '\'' +
                ", nationalite='" + nationalite + '\'' +
                ", code='" + code + '\'' +
                ", indicatif=" + indicatif +
                '}';
    }
}
