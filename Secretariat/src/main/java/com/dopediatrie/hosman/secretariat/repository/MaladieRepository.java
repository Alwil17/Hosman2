package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Maladie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MaladieRepository extends JpaRepository<Maladie,Long> {

    Boolean existsByNom(String nom);

    @Query("SELECT p from Maladie p where p.nom like concat('%',:nom,'%')")
    List<Maladie> findByNom(@Param("nom") String nom);

}