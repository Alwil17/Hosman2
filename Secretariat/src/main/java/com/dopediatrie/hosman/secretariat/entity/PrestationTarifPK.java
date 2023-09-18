package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PrestationTarifPK implements Serializable {
    public long prestation_id;
    public long tarif_id;
}
