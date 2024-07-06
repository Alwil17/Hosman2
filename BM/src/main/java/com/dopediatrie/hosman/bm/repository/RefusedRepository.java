package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Refused;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RefusedRepository extends JpaRepository<Refused, Long> {
    @Query("select s from Refused s where s.consultation.id = :hId")
    Optional<Refused> findAllByConsultationId(@Param("hId") long consultationId);

    @Query("select s from Refused s where s.motif = :mRef and s.consultation.id = :hId")
    Optional<Refused> findByMotifAndConsultationId(@Param("mRef") String motif, @Param("hId")  long consultation_id);

    @Query("select case when count(s)>0 then true else false end from Refused s where s.motif = :mRef and s.consultation.id = :hId")
    boolean existsByMed_refAndConsultationId(@Param("mRef") String motif, @Param("hId")  long consultation_id);
}