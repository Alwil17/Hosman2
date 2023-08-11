package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PatientAssurancePK implements Serializable {
    private long patient_id;
    private long assurance_id;
}
