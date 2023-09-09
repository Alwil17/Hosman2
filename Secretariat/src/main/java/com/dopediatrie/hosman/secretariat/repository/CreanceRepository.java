package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Creance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CreanceRepository extends JpaRepository<Creance,Long> {
    @Query("select c from Creance c where c.montant > 0")
    List<Creance> findAllWithPositiveMontant();
}