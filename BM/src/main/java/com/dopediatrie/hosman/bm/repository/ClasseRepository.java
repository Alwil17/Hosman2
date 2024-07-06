package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClasseRepository extends JpaRepository<Classe,Long> {
    boolean existsBySlug(String slug);
    Optional<Classe> findBySlug(String slug);

    @Query("select c from Classe c where concat(c.nom, c.slug) like concat('%', :q,'%') ")
    List<Classe> findByQueryString(@Param("q") String q);
}