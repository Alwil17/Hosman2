package com.dopediatrie.hosman.hospi.repository;

import com.dopediatrie.hosman.hospi.entity.Suivi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SuiviRepository extends JpaRepository<Suivi, Long> {
    @Query("select s from Suivi s where s.apply_date = :aDate and s.type = :type and s.type_id = :tId and s.hospit.id = :hId")
    Optional<Suivi> findByApply_dateAndTypeAndType_idAndHospitId(@Param("aDate") LocalDateTime apply_date, @Param("type") String type, @Param("tId") long type_id, @Param("hId")  long hospi_id);

    @Query("select case when count(s)>0 then true else false end from Suivi s where s.apply_date = :aDate and s.type = :type and s.type_id = :tId and s.hospit.id = :hId")
    boolean existsByApply_dateAndTypeAndType_idAndHospitId(@Param("aDate") LocalDateTime apply_date, @Param("type") String type, @Param("tId") long type_id, @Param("hId")  long hospi_id);
}