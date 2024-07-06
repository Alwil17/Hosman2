package com.dopediatrie.hosman.bm.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ProduitClassePK implements Serializable {
    public long produit_id;
    public long classe_id;
}
