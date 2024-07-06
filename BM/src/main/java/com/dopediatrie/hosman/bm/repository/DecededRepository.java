package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Deceded;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DecededRepository extends JpaRepository<Deceded, Long> {
    @Query("select s from Deceded s where s.consultation.id = :hId")
    Optional<Deceded> findAllByConsultationId(@Param("hId") long consultationId);
}