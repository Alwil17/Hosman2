package com.dopediatrie.hosman.hospi.repository;

import com.dopediatrie.hosman.hospi.entity.Lit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LitRepository extends JpaRepository<Lit,Long> {
    boolean existsByNom(String nom);
    Optional<Lit> findByNomEquals(String nom);
    List<Lit> findByNomLike(String nom);
    boolean existsBySlug(String slug);
    Optional<Lit> findBySlugEquals(String slug);

    @Query("select case when count(lit)>0 then true else false end from Lit lit JOIN lit.chambre ch where ch.id = :chambreId")
    boolean existsByChambreId(@Param("chambreId") long chambre_id);

    @Query("select lit from Lit lit JOIN lit.chambre ch where ch.id = :chambreId")
    List<Lit> findByChambreId(@Param("chambreId") long chambre_id);

    @Query("select case when count(lit)>0 then true else false end from Lit lit JOIN lit.chambre ch where ch.nom = :chambreNom")
    boolean existsByChambreNom(@Param("chambreNom") String chambre_nom);

    @Query("select lit from Lit lit JOIN lit.chambre ch where ch.nom = :chambreNom")
    List<Lit> findByChambreNom(@Param("chambreNom") String chambre_nom);

    @Query("select lit from Lit lit LEFT JOIN Suivi s on s.type_id = lit.id and s.type = 'lits' WHERE s.id IS NULL")
    List<Lit> findAllUntaken();

    @Query("select lit from Lit lit JOIN lit.chambre ch LEFT JOIN Suivi s on s.type_id = lit.id and s.type = 'lits' WHERE ch.id = :cId and s.id IS NULL")
    List<Lit> findAllUntakenByCHambreId(@Param("cId") long chambreId);
}