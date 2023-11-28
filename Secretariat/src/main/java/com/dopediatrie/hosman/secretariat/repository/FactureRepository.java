package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FactureRepository extends JpaRepository<Facture,Long> {

    Optional<Facture> findByCreanceId(long creanceId);

    boolean existsByCreanceId(long creanceId);

    @Query("SELECT f from Facture f where f.date_facture >= :datemin and f.date_facture <= :datemax")
    List<Facture> getAllByDateminAndDatemax(@Param("datemin") LocalDateTime datemin, @Param("datemax") LocalDateTime datemax);

    @Query("SELECT f from Facture f JOIN Prestation p ON f.prestation.id = p.id JOIN PrestationTarif pt ON p.id = pt.prestation.id JOIN Tarif t ON pt.tarif.id = t.id JOIN Acte a ON t.acte.id = a.id JOIN Groupe g ON a.groupe.id = g.id where f.date_facture >= :datemin and f.date_facture <= :datemax and g.code = :code")
    List<Facture> getAllByDateminAndDatemaxAndCode(@Param("datemin") LocalDateTime datemin, @Param("datemax") LocalDateTime datemax, @Param("code") String code);

    @Query("SELECT f from Facture f JOIN Prestation p ON f.prestation.id = p.id JOIN Patient pt ON pt.id = p.patient.id where f.date_facture >= :datemin and f.date_facture <= :datemax and concat(pt.nom,' ', pt.prenoms) like concat('%',:patient,'%')")
    List<Facture> getAllByDateminAndDatemaxAndPatient(@Param("datemin") LocalDateTime datemin, @Param("datemax") LocalDateTime datemax, @Param("patient") String patient);

    @Query("SELECT f from Facture f JOIN Prestation p ON f.prestation.id = p.id JOIN Patient pat ON pat.id = p.patient.id JOIN PrestationTarif pt ON p.id = pt.prestation.id JOIN Tarif t ON pt.tarif.id = t.id JOIN Acte a ON t.acte.id = a.id JOIN Groupe g ON a.groupe.id = g.id where f.date_facture >= :datemin and f.date_facture <= :datemax and g.code = :code and concat(pat.nom,' ', pat.prenoms) like concat('%',:patient,'%')")
    List<Facture> getAllByDateminAndDatemaxAndCodeAndPatient(@Param("datemin") LocalDateTime datemin, @Param("datemax") LocalDateTime datemax, @Param("code") String code, @Param("patient") String patient);
}