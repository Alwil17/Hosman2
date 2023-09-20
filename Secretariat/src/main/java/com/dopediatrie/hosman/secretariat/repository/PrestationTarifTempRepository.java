package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.PrestationTarifTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PrestationTarifTempRepository extends JpaRepository<PrestationTarifTemp,Long> {
    @Query("select pt from PrestationTarifTemp pt where pt.prestation.id = :prestationId")
    List<PrestationTarifTemp> findByPrestation_tempId(@Param("prestationId") long prestationId);

    //@Query("delete from PrestationTarifTemp pt where pt.prestation_temp.id = :prestationId and pt.tarif.id = :tarifId")
    @Transactional
    void deleteByPrestationIdAndTarifId(@Param("prestationId") long prestationId, @Param("tarifId") long tarifId);
}