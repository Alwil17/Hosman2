package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {
    boolean existsByProduitId(long produitId);
    List<Prescription> findByProduitId(long produitId);
}