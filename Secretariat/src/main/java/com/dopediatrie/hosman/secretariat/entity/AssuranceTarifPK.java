package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AssuranceTarifPK implements Serializable {
    public long assurance_id;
    public long tarif_id;
}
