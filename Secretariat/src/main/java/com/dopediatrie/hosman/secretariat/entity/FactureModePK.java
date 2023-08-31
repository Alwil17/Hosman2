package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FactureModePK implements Serializable {
    public long facture_id;
    public long mode_payement_id;
}
