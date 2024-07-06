package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Forme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FormeRepository extends JpaRepository<Forme,Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Forme f WHERE f.produit.id = :produitId")
    void deleteAllForProduitId(@Param("produitId") long produitId);
}