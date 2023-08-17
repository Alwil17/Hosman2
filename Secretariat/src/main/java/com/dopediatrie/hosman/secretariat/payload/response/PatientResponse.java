package com.dopediatrie.hosman.secretariat.payload.response;

import com.dopediatrie.hosman.secretariat.entity.Employeur;
import com.dopediatrie.hosman.secretariat.entity.Pays;
import com.dopediatrie.hosman.secretariat.entity.PersonneAPrevenir;
import com.dopediatrie.hosman.secretariat.entity.Profession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {
    private long id;
    private String reference;
    private String nom;
    private String prenoms;
    private LocalDateTime date_naissance;
    private char sexe;
    private String lieu_naissance;
    private String tel1;
    private String tel2;
    private String email;
    private String type_piece;
    private String no_piece;
    private int is_assure;
    private Pays pays_origine;
    private Profession profession;
    private Employeur employeur;
    private PersonneAPrevenir personne_a_prevenir;
    private long structure_id;
}
