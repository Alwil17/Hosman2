package com.dopediatrie.hosman.hospi.repository;

import com.dopediatrie.hosman.hospi.entity.Transfused;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransfusedRepository extends JpaRepository<Transfused, Long> {
    @Query("select s from Transfused s where s.hospit.id = :hId")
    List<Transfused> findAllByHospitId(@Param("hId") long hospitId);
}