package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CreanceModePK implements Serializable {
    public long creance_id;
    public long mode_payement_id;
}
