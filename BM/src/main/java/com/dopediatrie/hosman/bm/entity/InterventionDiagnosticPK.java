package com.dopediatrie.hosman.bm.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class InterventionDiagnosticPK implements Serializable {
    public long intervention_id;
    public long diagnostic_id;
}
