package com.dopediatrie.hosman.stock.repository;

import com.dopediatrie.hosman.stock.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit,Long> {

    @Query("SELECT p from Produit p where concat(p.nom,' ', p.nom_officiel) like concat('%',:nom,'%')")
    List<Produit> findByNomLike(@Param("nom") String nom);

    @Query("SELECT p from Produit p where p.dci like concat('%',:dci,'%')")
    List<Produit> findByDciLike(@Param("dci") String dci);

    @Query("SELECT p from Produit p where p.code_acte like concat('%',:code_acte,'%')")
    List<Produit> findByCodeActeLike(@Param("code_acte") String code_acte);
}