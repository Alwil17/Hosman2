package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Intervention {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private long patient_id;
    private LocalDateTime date_entree;
    private LocalDateTime date_sortie;
    private String commentaires;
}
