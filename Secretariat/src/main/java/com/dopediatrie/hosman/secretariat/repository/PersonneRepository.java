package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonneRepository extends JpaRepository<Personne,Long> {
    @Query("SELECT p from Personne p where concat(p.nom,' ', p.prenoms) = ':nom :prenoms'")
    Optional<Personne> searchByNomAndPrenoms(@Param("nom") String nom, @Param("prenoms") String prenoms);

    @Query("SELECT p from Personne p where concat(p.nom,' ', p.prenoms) = ':nom :prenoms'")
    Boolean existsByNomAndPrenoms(@Param("nom") String nom, @Param("prenoms") String prenoms);
}