package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Attente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AttenteRepository extends JpaRepository<Attente,Long> {
    @Query("select a from Attente a where a.attente = true ")
    List<Attente> findAll();

    @Query("SELECT MAX(a.num_attente) FROM Attente a ")
    Long getMaxNumAttente();

    @Query("select case when count(a)>0 then true else false end from Attente a where a.num_attente = :numAttente")
    boolean existsByNum_attente(@Param("numAttente") long num_attente);

    @Query("select a from Attente a where a.num_attente = :numAttente")
    Optional<Attente> findByNum_attente(@Param("numAttente") long num_attente);

    @Query("select a from Attente a where a.secteur_code = :code and a.attente = true ")
    List<Attente> getAttenteBySecteurCode(String code);

    @Query("select a from Attente a where a.medecin = :matricule and a.attente = true ")
    List<Attente> getAttenteByMedecin(String matricule);
}