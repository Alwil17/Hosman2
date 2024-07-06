package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Posologie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PosologieRepository extends JpaRepository<Posologie,Long> {
    boolean existsBySlug(String slug);
    Optional<Posologie> findBySlug(String slug);

    List<Posologie> findByType(String type);

    @Query("select p from Posologie p where type = :type and p.libelle like concat('%', :q,'%') ")
    List<Posologie> findByTypeAndQueryString(@Param("type") String type, @Param("q") String q);
}