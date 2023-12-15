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
public class Employeur {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String nom;
    private String tel1;
    private String tel2;
    private String email;
    @OneToMany(mappedBy = "employeur")
    @JsonIgnore
    private List<Patient> patients;
    @OneToMany(mappedBy = "employeur")
    @JsonIgnore
    private List<Filiation> filiations;

    @Override
    public String toString() {
        return "Employeur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", tel1='" + tel1 + '\'' +
                ", tel2='" + tel2 + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
