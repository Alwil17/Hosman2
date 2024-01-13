package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Annuaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnuaireRepository extends JpaRepository<Annuaire,Long> {

    @Query("select a from Annuaire a where a.categorie_slug = :categorie")
    List<Annuaire> findByCategorie(String categorie);

    @Query("select a from Annuaire a where concat(a.nom, a.prenom, a.profession, a.email) like concat('%', :q, '%') ")
    List<Annuaire> findByQueryString(@Param("q") String searchString);

    @Query("select a from Annuaire a where a.categorie_slug = :cat and concat(a.nom, a.prenom, a.profession, a.email) like concat('%', :q, '%') ")
    List<Annuaire> findByCategorieAndQueryString(@Param("cat") String cat, @Param("q") String searchString);
}