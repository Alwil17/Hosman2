package com.dopediatrie.hosman.stock.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "produit_classe")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProduitClasse {
    @EmbeddedId
    private ProduitClassePK id;

    @ManyToOne
    @MapsId("produit_id")
    @JoinColumn(name = "produit_id")
    private Produit produit;
    @ManyToOne
    @MapsId("classe_id")
    @JoinColumn(name = "classe_id")
    private Classe classe;
}
