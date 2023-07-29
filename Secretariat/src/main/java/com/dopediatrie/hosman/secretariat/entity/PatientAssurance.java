package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientAssurance {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private long patient_id;
    private long assurance_id;
    private double taux;
    private Date date_debut;
    private Date date_fin;
}
