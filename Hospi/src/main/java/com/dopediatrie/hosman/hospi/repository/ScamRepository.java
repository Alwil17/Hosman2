package com.dopediatrie.hosman.hospi.repository;

import com.dopediatrie.hosman.hospi.entity.Scam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScamRepository extends JpaRepository<Scam, Long> {
    @Query("select s from Scam s where s.hospit.id = :hId")
    List<Scam> findAllByHospitId(@Param("hId") long hospitId);
}