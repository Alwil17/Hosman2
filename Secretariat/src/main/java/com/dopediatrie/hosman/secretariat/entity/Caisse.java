package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Caisse {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String libelle;
    private String slug;
    private LocalDateTime date_ouverture;
    private LocalDateTime date_fermeture;
    private boolean ouvert = true;
    private double montant = 0;
    private long caissiere_id;
    private long structure_id;

    @Override
    public String toString() {
        return "Caisse{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", slug='" + slug + '\'' +
                ", date_ouverture=" + date_ouverture +
                ", date_fermeture=" + date_fermeture +
                ", ouvert=" + ouvert +
                ", montant=" + montant +
                ", caissiere_id=" + caissiere_id +
                ", structure_id=" + structure_id +
                '}';
    }
}