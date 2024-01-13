package com.dopediatrie.hosman.secretariat.payload.response;

import com.dopediatrie.hosman.secretariat.entity.Assurance;
import com.dopediatrie.hosman.secretariat.entity.Employeur;
import com.dopediatrie.hosman.secretariat.entity.Profession;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FiliationResponse {
    private long id;
    private String nom;
    private String prenoms;
    private String telephone;
    private char sexe;
    private String type;
    private int annee_naissance;
    private ProfessionResponse profession;
    private EmployeurResponse employeur;
    private AssuranceResponse assurance;
}
