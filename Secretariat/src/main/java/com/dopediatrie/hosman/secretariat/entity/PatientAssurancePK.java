package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PatientAssurancePK implements Serializable {
    public long patient_id;
    public long assurance_id;
}
