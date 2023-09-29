package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Reliquat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReliquatRepository extends JpaRepository<Reliquat,Long> {
    @Query("select c from Reliquat c where c.montant > 0")
    List<Reliquat> findAllWithPositiveMontant();

    @Query("SELECT p from Reliquat p where p.facture.id = :factureId")
    Optional<Reliquat> findByFactureId(@Param("factureId") long factureId);

    @Query("SELECT p from Reliquat p where p.facture.id = :factureId")
    Boolean existsByFactureId(@Param("factureId") long factureId);

    @Query("SELECT c from Reliquat c where c.date_operation >= :datemin and c.date_operation <= :datemax and c.montant > 0")
    List<Reliquat> getAllByDateminAndDatemax(LocalDateTime datemin, LocalDateTime datemax);

    @Query("SELECT c from Reliquat c JOIN Patient p on c.patient.id = p.id where c.date_operation >= :datemin and c.date_operation <= :datemax and c.montant > 0 and concat(p.nom,' ', p.prenoms) like concat('%',:nom,'%')")
    List<Reliquat> getAllByDateminAndDatemaxAndNom(LocalDateTime datemin, LocalDateTime datemax, String nom);

    @Query("SELECT c from Reliquat c JOIN Facture f on c.facture.id = f.id where c.date_operation >= :datemin and c.date_operation <= :datemax and c.montant > 0 and f.reference like concat('%',:reference,'%')")
    List<Reliquat> getAllByDateminAndDatemaxAndReference(LocalDateTime datemin, LocalDateTime datemax, String reference);
}