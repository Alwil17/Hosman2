package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.FactureMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FactureModeRepository extends JpaRepository<FactureMode,Long> {

    @Query("SELECT em from FactureMode em where em.facture.id = :factureId and em.mode_payement.id = :modeId")
    Optional<FactureMode> findByFacture_IdAndMode_payement_Id(@Param("factureId") long facture_id, @Param("modeId") long mode_payement_id);

    @Query("SELECT em from FactureMode em where em.facture.id = :factureId and em.mode_payement.id = :modeId")
    Boolean existsByFacture_IdAndMode_payement_Id(@Param("factureId") long facture_id, @Param("modeId") long mode_payement_id);

    @Query("SELECT em from FactureMode em where em.facture.id = :factureId")
    List<FactureMode> findByFacture_Id(@Param("factureId") long factureId);
}