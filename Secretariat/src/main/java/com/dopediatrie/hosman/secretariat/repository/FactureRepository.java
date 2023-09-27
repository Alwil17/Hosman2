package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FactureRepository extends JpaRepository<Facture,Long> {

    @Query("SELECT f from Facture f where f.date_facture >= :datemin and f.date_facture <= :datemax")
    List<Facture> getAllByDateminAndDatemax(@Param("datemin") LocalDateTime datemin, @Param("datemax") LocalDateTime datemax);

    @Query("SELECT f from Facture f JOIN Prestation p ON f.prestation.id = p.id JOIN PrestationTarif pt ON p.id = pt.prestation.id JOIN Tarif t ON pt.tarif.id = t.id JOIN Acte a ON t.acte.id = a.id  where f.date_facture >= :datemin and f.date_facture <= :datemax and a.code = :code")
    List<Facture> getAllByDateminAndDatemaxAndCode(@Param("datemin") LocalDateTime datemin, @Param("datemax") LocalDateTime datemax, @Param("code") String code);
}