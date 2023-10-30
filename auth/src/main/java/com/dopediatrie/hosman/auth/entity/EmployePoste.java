package com.dopediatrie.hosman.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "employe_poste")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployePoste {
    @EmbeddedId
    private EmployePostePK id;

    @ManyToOne
    @MapsId("employe_id")
    @JoinColumn(name = "employe_id")
    private Employe employe;
    @ManyToOne
    @MapsId("poste_id")
    @JoinColumn(name = "poste_id")
    private Poste poste;
}