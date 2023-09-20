package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PrestationTarifTempPK implements Serializable {
    public long prestation_temp_id;
    public long tarif_id;
}
