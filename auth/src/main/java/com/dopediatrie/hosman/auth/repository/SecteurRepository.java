package com.dopediatrie.hosman.auth.repository;

import com.dopediatrie.hosman.auth.entity.Secteur;
import com.dopediatrie.hosman.auth.payload.response.SecteurResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SecteurRepository extends JpaRepository<Secteur,Long> {
    boolean existsByCode(String code);
    Optional<Secteur> findByCodeEquals(String code);

    @Query("SELECT s from Secteur s JOIN s.departement d where d.code = :departement ")
    List<Secteur> findByDepartement(String departement);

    @Query("SELECT s from Secteur s JOIN s.departement d where d.code = :departement and s.code = :code")
    List<Secteur> findByDepartementAndCode(@Param("departement") String departement, @Param("code") String code);

    @Query("select s from Secteur s JOIN Employe e on e.secteur.id = s.id JOIN User u on e.user.id = u.id and u.id = :userId")
    Optional<Secteur> findByUserId(@Param("userId") long userId);
}
