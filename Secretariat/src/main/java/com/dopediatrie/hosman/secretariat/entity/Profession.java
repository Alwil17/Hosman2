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
public class Profession {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String denomination;
    private String slug;
    @OneToMany(mappedBy = "profession")
    @JsonIgnore
    private List<Patient> patients;

    @Override
    public String toString() {
        return "Profession{" +
                "id=" + id +
                ", denomination='" + denomination + '\'' +
                ", slug='" + slug + '\'' +
                '}';
    }
}
