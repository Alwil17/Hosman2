package com.dopediatrie.hosman.hospi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lit {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String nom;
    private String slug;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    @ManyToOne
    @JoinColumn(name = "chambre_id")
    private Chambre chambre;
    /*@OneToMany(mappedBy = "lit")
    @JsonIgnore
    private List<Hospit> hospits;*/

    @Override
    public String toString() {
        return "Lit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", slug='" + slug + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
