package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Patient;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient,Long> {

    @Query("SELECT p from Patient p where concat(p.nom,' ', p.prenoms) like concat('%',:nom,'%')")
    List<Patient> findByNomAndPrenomsLike(@Param("nom") String nom);

    @Query("SELECT p from Patient p where p.reference like concat('%',:reference,'%') ")
    List<Patient> findByReferenceLike(@Param("reference") String reference);

    @Query("SELECT p from Patient p where p.reference = :reference")
    Optional<Patient> findByReferenceEquals(@Param("reference") String reference);

    @Query("SELECT p from Patient p where p.prenoms like concat('%',:prenoms,'%')")
    List<Patient> findByPrenomsLike(@Param("prenoms") String prenoms);

    @Query("SELECT p from Patient p where p.date_naissance >= :naiss and p.date_naissance <= :naissLimit")
    List<Patient> findAllByDate_naissance(@Param("naiss") LocalDateTime dateNaissance, @Param("naissLimit") LocalDateTime dateNaissanceLimit);

    @Query("SELECT p from Patient p where p.date_ajout >= :entree and p.date_ajout <= :entreeLimit")
    List<Patient> findAllByDate_ajout(@Param("entree") LocalDateTime dateEntree, @Param("entreeLimit") LocalDateTime dateEntreeLimit);
}