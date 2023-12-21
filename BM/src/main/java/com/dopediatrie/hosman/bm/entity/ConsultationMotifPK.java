package com.dopediatrie.hosman.bm.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ConsultationMotifPK implements Serializable {
    public long consultation_id;
    public long motif_id;
}
