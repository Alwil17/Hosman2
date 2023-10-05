package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Caisse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CaisseRepository extends JpaRepository<Caisse,Long> {

    @Query("select c from Caisse c where c.date_ouverture >= :datemin and c.date_ouverture < :datemax and c.ouvert = :ouvert")
    Optional<Caisse> getCurrentCaisse(@Param("datemin") LocalDateTime datemin, @Param("datemax") LocalDateTime datemax, boolean ouvert);

    @Query("select c from Caisse c where c.date_ouverture >= :datemin and c.date_ouverture < :datemax and c.ouvert = :ouvert")
    Boolean existsCurrentCaisse(@Param("datemin") LocalDateTime datemin, @Param("datemax") LocalDateTime datemax, boolean ouvert);

    @Query("update Caisse c set c.ouvert = false where c.ouvert = true")
    void closeAllCaisse();

    Optional<Caisse> findByLibelle(String libelle);
    Boolean existsByLibelle(String libelle);
    Optional<Caisse> findByOuvert(boolean ouvert);
    Boolean existsByOuvert(boolean ouvert);

    @Query("select c from Caisse c where c.date_ouverture >= :datemin and (c.date_fermeture is null or c.date_fermeture <= :datemax)")
    List<Caisse> findByDateminAndDatemax(LocalDateTime datemin, LocalDateTime datemax);
}