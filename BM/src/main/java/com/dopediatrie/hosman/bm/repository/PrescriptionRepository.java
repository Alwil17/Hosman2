package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {
    boolean existsByProduitId(long produitId);
    List<Prescription> findByProduitId(long produitId);

    @Query("select p from Prescription p where p.mu = true")
    List<Prescription> getMU();
}