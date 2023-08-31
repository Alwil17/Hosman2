package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class EncaissementModePK implements Serializable {
    public long encaissement_id;
    public long mode_payement_id;
}
