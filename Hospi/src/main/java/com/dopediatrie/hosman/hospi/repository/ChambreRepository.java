package com.dopediatrie.hosman.hospi.repository;

import com.dopediatrie.hosman.hospi.entity.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChambreRepository extends JpaRepository<Chambre,Long> {
    boolean existsByNom(String nom);
    Optional<Chambre> findByNomEquals(String nom);
    List<Chambre> findByNomLike(String nom);
    boolean existsBySlug(String slug);
    Optional<Chambre> findBySlugEquals(String slug);
}