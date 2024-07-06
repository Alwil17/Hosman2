package com.dopediatrie.hosman.bm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EffetSecondaire {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String libelle;
    private String slug;
    @ManyToOne
    @JoinColumn(name = "produit_id")
    @JsonIgnore
    private Produit produit;
}
