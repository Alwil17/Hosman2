package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Addressed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AddressedRepository extends JpaRepository<Addressed, Long> {
    @Query("select s from Addressed s where s.consultation.id = :hId")
    Optional<Addressed> findAllByConsultationId(@Param("hId") long consultationId);

    @Query("select s from Addressed s where s.med_ref = :mRef and s.consultation.id = :hId")
    Optional<Addressed> findByMed_refAndConsultationId(@Param("mRef") String med_ref, @Param("hId")  long consultation_id);

    @Query("select case when count(s)>0 then true else false end from Addressed s where s.med_ref = :mRef and s.consultation.id = :hId")
    boolean existsByMed_refAndConsultationId(@Param("mRef") String med_ref, @Param("hId")  long consultation_id);
}