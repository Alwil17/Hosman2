package com.dopediatrie.hosman.bm.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class InterventionMotifPK implements Serializable {
    public long intervention_id;
    public long motif_id;
}
