package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.PrestationTarif;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestationTarifRepository extends JpaRepository<PrestationTarif,Long> {
    List<PrestationTarif> findByPrestationId(long prestationId);
}