package com.dopediatrie.hosman.hospi.repository;

import com.dopediatrie.hosman.hospi.entity.Addressed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AddressedRepository extends JpaRepository<Addressed, Long> {
    @Query("select s from Addressed s where s.hospit.id = :hId")
    List<Addressed> findAllByHospitId(@Param("hId") long hospitId);

    @Query("select s from Addressed s where s.med_ref = :mRef and s.hospit.id = :hId")
    Optional<Addressed> findByMed_refAndHospitId(@Param("mRef") String med_ref, @Param("hId")  long hospit_id);

    @Query("select case when count(s)>0 then true else false end from Addressed s where s.med_ref = :mRef and s.hospit.id = :hId")
    boolean existsByMed_refAndHospitId(@Param("mRef") String med_ref, @Param("hId")  long hospit_id);
}