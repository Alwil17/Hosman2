package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Creance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CreanceRepository extends JpaRepository<Creance,Long> {
    @Query("select c from Creance c where c.montant > 0")
    List<Creance> findAllWithPositiveMontant();

    @Query("SELECT c from Creance c where c.date_operation >= :datemin and c.date_operation <= :datemax and c.montant > 0")
    List<Creance> getAllByDateminAndDatemax(LocalDateTime datemin, LocalDateTime datemax);

    @Query("SELECT c from Creance c JOIN Patient p on c.patient.id = p.id where c.date_operation >= :datemin and c.date_operation <= :datemax and c.montant > 0 and concat(p.nom,' ', p.prenoms) like concat('%',:nom,'%')")
    List<Creance> getAllByDateminAndDatemaxAndNom(LocalDateTime datemin, LocalDateTime datemax, String nom);

    @Query("SELECT c from Creance c JOIN Facture f on c.facture.id = f.id where c.date_operation >= :datemin and c.date_operation <= :datemax and c.montant > 0 and f.reference like concat('%',:reference,'%')")
    List<Creance> getAllByDateminAndDatemaxAndReference(LocalDateTime datemin, LocalDateTime datemax, String reference);
}