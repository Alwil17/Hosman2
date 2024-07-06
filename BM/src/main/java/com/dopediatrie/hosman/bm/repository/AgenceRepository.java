package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AgenceRepository extends JpaRepository<Agence,Long> {

    @Query("select a from Agence a where concat(a.nom, a.slug, a.email) like concat('%', :q,'%') ")
    List<Agence> findAllByQueryString(@Param("q") String q);

    boolean existsBySlug(String slug);
    Optional<Agence> findBySlug(String slug);
}