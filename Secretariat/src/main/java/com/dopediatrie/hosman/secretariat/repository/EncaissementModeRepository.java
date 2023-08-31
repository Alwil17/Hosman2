package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.EncaissementMode;
import com.dopediatrie.hosman.secretariat.entity.EncaissementModePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EncaissementModeRepository extends JpaRepository<EncaissementMode,Long> {

    @Query("SELECT em from EncaissementMode em where em.encaissement.id = :encaissementId and em.mode_payement.id = :modeId")
    Optional<EncaissementMode> findByEncaissement_IdAndMode_payement_Id(@Param("encaissementId") long encaissement_id, @Param("modeId") long mode_payement_id);

    @Query("SELECT em from EncaissementMode em where em.encaissement.id = :encaissementId and em.mode_payement.id = :modeId")
    Boolean existsByEncaissement_IdAndMode_payement_Id(@Param("encaissementId") long encaissement_id, @Param("modeId") long mode_payement_id);
}