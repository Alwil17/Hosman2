package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.PersonneAPrevenir;
import com.dopediatrie.hosman.secretariat.entity.Tarif;
import com.dopediatrie.hosman.secretariat.payload.response.TarifResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TarifRepository extends JpaRepository<Tarif,Long> {

    @Query("SELECT tarif FROM Tarif tarif JOIN tarif.acte acte JOIN acte.groupe groupe WHERE groupe.id = :gid")
    List<Tarif> findTarifsByGroupeId(@Param("gid") long groupeId);

    @Query("SELECT tarif FROM Tarif tarif JOIN tarif.acte acte JOIN acte.groupe groupe WHERE groupe.code = ':gcode'")
    List<Tarif> findTarifsByGroupeCode(@Param("gcode") String groupeCode);
}