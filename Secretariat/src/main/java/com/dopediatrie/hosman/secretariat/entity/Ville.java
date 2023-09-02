package com.dopediatrie.hosman.secretariat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ville {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String nom;
    private String slug;
    @OneToMany(mappedBy = "ville")
    @JsonIgnore
    private List<Adresse> adresses;

    @Override
    public String toString() {
        return "Ville{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
