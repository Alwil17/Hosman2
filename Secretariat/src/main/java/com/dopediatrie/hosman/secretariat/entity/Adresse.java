package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Adresse {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String arrondissement;
    private String no_maison;
    private String rue;
    private String bp;
    private long ville_id;
    private long quartier_id;
}
