package com.dopediatrie.hosman.auth.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class EmployePostePK implements Serializable {
    public long employe_id;
    public long poste_id;
}