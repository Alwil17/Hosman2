package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Attente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AttenteRepository extends JpaRepository<Attente,Long> {
    @Query("SELECT MAX(a.num_attente) FROM Attente a")
    Long getMaxNumAttente();

    @Query("select a from Attente a where a.secteur_code = :code")
    List<Attente> getAttenteBySecteurCode(String code);

    @Query("select a from Attente a where a.medecin = :matricule")
    List<Attente> getAttenteByMedecin(String matricule);
}