package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.CreanceMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CreanceModeRepository extends JpaRepository<CreanceMode,Long> {

    @Query("SELECT em from CreanceMode em where em.creance.id = :creanceId and em.mode_payement.id = :modeId")
    Optional<CreanceMode> findByCreance_IdAndMode_payement_Id(@Param("creanceId") long creance_id, @Param("modeId") long mode_payement_id);

    @Query("SELECT em from CreanceMode em where em.creance.id = :creanceId and em.mode_payement.id = :modeId")
    Boolean existsByCreance_IdAndMode_payement_Id(@Param("creanceId") long creance_id, @Param("modeId") long mode_payement_id);
}