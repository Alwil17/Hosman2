package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Transfered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransferedRepository extends JpaRepository<Transfered, Long> {
    @Query("select s from Transfered s where s.consultation.id = :hId")
    Optional<Transfered> findAllByConsultationId(@Param("hId") long consultationId);

    @Query("select s from Transfered s where s.motif = :mRef and s.consultation.id = :hId")
    Optional<Transfered> findByMotifAndConsultationId(@Param("mRef") String motif, @Param("hId")  long consultation_id);

    @Query("select case when count(s)>0 then true else false end from Transfered s where s.motif = :mRef and s.consultation.id = :hId")
    boolean existsByMed_refAndConsultationId(@Param("mRef") String motif, @Param("hId")  long consultation_id);
}