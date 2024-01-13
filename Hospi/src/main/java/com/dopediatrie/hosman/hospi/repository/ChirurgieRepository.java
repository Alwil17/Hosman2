package com.dopediatrie.hosman.hospi.repository;

import com.dopediatrie.hosman.hospi.entity.Chirurgie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChirurgieRepository extends JpaRepository<Chirurgie, Long> {
    @Query("select s from Chirurgie s where s.hospit.id = :hId")
    List<Chirurgie> findAllByHospitId(@Param("hId") long hospitId);

    @Query("select s from Chirurgie s where s.title = :tOp and s.med_ref = :mRef and s.hospit.id = :hId")
    Optional<Chirurgie> findByMed_refAndTitleAndHospitId(@Param("mRef") String med_ref, @Param("tOp") String title, @Param("hId")  long hospit_id);

    @Query("select case when count(s)>0 then true else false end from Chirurgie s where s.title = :tOp and s.med_ref = :mRef and s.hospit.id = :hId")
    boolean existsByMed_refAndTitleAndHospitId(@Param("mRef") String med_ref, @Param("tOp") String title, @Param("hId")  long hospit_id);


}