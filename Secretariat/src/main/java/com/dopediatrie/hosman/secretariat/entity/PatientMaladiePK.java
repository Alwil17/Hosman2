package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PatientMaladiePK implements Serializable {
    public long patient_id;
    public long maladie_id;
}
