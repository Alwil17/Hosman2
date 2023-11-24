package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.PersonneAPrevenir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonneAPrevenirRepository extends JpaRepository<PersonneAPrevenir,Long> {

    @Query("SELECT p from PersonneAPrevenir p where concat(p.nom,' ', p.prenoms) = ':nom :prenoms'")
    Optional<PersonneAPrevenir> searchByNomAndPrenoms(@Param("nom") String nom, @Param("prenoms") String prenoms);

    @Query("SELECT case when count(p)>0 then true else false end from PersonneAPrevenir p where concat(p.nom,' ', p.prenoms) = ':nom :prenoms'")
    Boolean existsByNomAndPrenoms(@Param("nom") String nom, @Param("prenoms") String prenoms);
}