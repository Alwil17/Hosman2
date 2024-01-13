package com.dopediatrie.hosman.hospi.repository;

import com.dopediatrie.hosman.hospi.entity.Sortie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SortieRepository extends JpaRepository<Sortie, Long> {
    @Query("select s from Sortie s where s.hospit.id = :hId")
    List<Sortie> findAllByHospitId(@Param("hId") long hospitId);
}