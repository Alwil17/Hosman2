package com.dopediatrie.hosman.hospi.repository;

import com.dopediatrie.hosman.hospi.entity.Addressed;
import com.dopediatrie.hosman.hospi.entity.Deceded;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DecededRepository extends JpaRepository<Deceded, Long> {
    @Query("select s from Deceded s where s.hospit.id = :hId")
    List<Deceded> findAllByHospitId(@Param("hId") long hospitId);

    @Query("select s from Addressed s where s.specialite = :tOp and s.med_ref = :mRef and s.hospit.id = :hId")
    Optional<Addressed> findByMed_refAndTitleAndHospitId(@Param("mRef") String med_ref, @Param("tOp") String specialite, @Param("hId")  long hospit_id);

    @Query("select case when count(s)>0 then true else false end from Addressed s where s.specialite = :tOp and s.med_ref = :mRef and s.hospit.id = :hId")
    boolean existsByMed_refAndTitleAndHospitId(@Param("mRef") String med_ref, @Param("tOp") String specialite, @Param("hId")  long hospit_id);
}