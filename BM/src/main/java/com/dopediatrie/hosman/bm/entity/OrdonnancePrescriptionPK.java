package com.dopediatrie.hosman.bm.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class OrdonnancePrescriptionPK implements Serializable {
    public long ordonnance_id;
    public long prescription_id;
}
