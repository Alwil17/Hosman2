package com.dopediatrie.hosman.hospi.repository;

import com.dopediatrie.hosman.hospi.entity.SortieDiagnostic ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SortieDiagnosticRepository extends JpaRepository<SortieDiagnostic , Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM SortieDiagnostic em WHERE em.sortie.id = :sortieId")
    void deleteBySortieId(@Param("sortieId") long sortieId);
}