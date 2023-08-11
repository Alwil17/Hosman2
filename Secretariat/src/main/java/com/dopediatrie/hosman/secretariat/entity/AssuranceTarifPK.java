package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AssuranceTarifPK implements Serializable {
    private long assurance_id;
    private long tarif_id;
}
