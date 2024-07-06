package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit,Long> {

    @Query("SELECT p from Produit p where p.nom like concat('%',:nom,'%')")
    List<Produit> findByNomLike(@Param("nom") String nom);

    @Query("SELECT p from Produit p where p.dci like concat('%',:dci,'%')")
    List<Produit> findByDciLike(@Param("dci") String dci);

    @Query("SELECT p from Produit p JOIN Indication i on p.id = i.produit.id where i.libelle like concat('%',:indication,'%')")
    List<Produit> findByIndicationLike(@Param("indication") String indication);

    @Query("SELECT p from Produit p JOIN ProduitClasse pc on p.id = pc.produit.id JOIN Classe c on c.id = pc.classe.id where c.nom like concat('%',:classe,'%')")
    List<Produit> findByClasseLike(@Param("classe") String classe);

    @Query("SELECT p from Produit p JOIN p.laboratoire l where l.nom like concat('%',:labo,'%')")
    List<Produit> findByLaboLike(@Param("labo") String labo);

    @Query("SELECT p from Produit p Join Indication ind JOIN ProduitClasse pc on p.id = pc.produit.id JOIN Classe c on c.id = pc.classe.id where concat(p.nom, p.dci, ind.libelle, c.nom) like concat('%',:q,'%')")
    List<Produit> findByQueryString(@Param("q") String q);

    @Query("SELECT p from Produit p where p.agence.id = :id")
    List<Produit> findByAgenceId(@Param("id") long id);

    @Query("SELECT p from Produit p where p.laboratoire.id = :id")
    List<Produit> findByLaboratoireId(@Param("id") long id);

    @Query("SELECT p from Produit p where p.delegue.id = :id")
    List<Produit> findByDelegueId(@Param("id") long id);

    @Query("SELECT p from Produit p JOIN ProduitClasse pc on p.id = pc.produit.id JOIN Classe c on c.id = pc.classe.id where c.id = :id")
    List<Produit> findByClasseId(@Param("id") long id);

    @Query("SELECT p from Produit p where p.agence.id = :id and concat(p.nom, p.dci) like concat('%',:q,'%')")
    List<Produit> findByAgenceIdAndQueryString(@Param("id") long id, @Param("q") String q);

    @Query("SELECT p from Produit p where p.laboratoire.id = :id and concat(p.nom, p.dci) like concat('%',:q,'%')")
    List<Produit> findByLaboratoireIdAndQueryString(@Param("id") long id, @Param("q") String q);

    @Query("SELECT p from Produit p where p.delegue.id = :id and concat(p.nom, p.dci) like concat('%',:q,'%')")
    List<Produit> findByDelegueIdAndQueryString(@Param("id") long id, @Param("q") String q);

    @Query("SELECT p from Produit p JOIN ProduitClasse pc on p.id = pc.produit.id JOIN Classe c on c.id = pc.classe.id where c.id = :id and concat(p.nom, p.dci) like concat('%',:q,'%')")
    List<Produit> findByClasseIdAndQueryString(@Param("id") long id, @Param("q") String q);
}