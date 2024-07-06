package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.ProduitClasse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProduitClasseRepository extends JpaRepository<ProduitClasse,Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM ProduitClasse pc WHERE pc.produit.id = :produitId")
    void deleteAllByProduitId(@Param("produitId")  long produitId);
}