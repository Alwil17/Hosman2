package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategorieRepository extends JpaRepository<Categorie,Long> {
    Optional<Categorie> findBySlugEquals(String slug);
    List<Categorie> findByNomLike(String nom);

    Boolean existsBySlug(String slug);
}