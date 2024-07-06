package com.dopediatrie.hosman.hospi.repository;

import com.dopediatrie.hosman.hospi.entity.Hospit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HospitRepository extends JpaRepository<Hospit,Long> {

    @Query("select h from Hospit h where h.status = :status")
    List<Hospit> findByStatus(@Param("status") int status);
}