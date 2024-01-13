package com.dopediatrie.hosman.hospi.repository;

import com.dopediatrie.hosman.hospi.entity.MedExterne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MedExterneRepository extends JpaRepository<MedExterne, Long> {
    @Query("select s from MedExterne s where s.hospit.id = :hId")
    List<MedExterne> findAllByHospitId(@Param("hId") long hospitId);

    @Query("select s from MedExterne s where s.type_op = :tOp and s.med_ref = :mRef and s.hospit.id = :hId")
    Optional<MedExterne> findByMed_refAndType_opAndHospitId(@Param("mRef") String med_ref, @Param("tOp") String type_op, @Param("hId")  long hospit_id);

    @Query("select case when count(s)>0 then true else false end from MedExterne s where s.type_op = :tOp and s.med_ref = :mRef and s.hospit.id = :hId")
    boolean existsByMed_refAndType_opAndHospitId(@Param("mRef") String med_ref, @Param("tOp") String type_op, @Param("hId")  long hospit_id);

}