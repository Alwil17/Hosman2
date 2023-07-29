package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Acte {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String libelle;
    private String slug;
    private long structure_id;
}