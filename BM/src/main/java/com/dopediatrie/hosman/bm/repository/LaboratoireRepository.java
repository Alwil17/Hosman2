package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Laboratoire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LaboratoireRepository extends JpaRepository<Laboratoire,Long> {

    boolean existsBySlug(String slug);
    Optional<Laboratoire> findBySlug(String Slug);

    @Query("select l from Laboratoire l where l.agence.id = :aId")
    List<Laboratoire> findByAgenceId(@Param("aId") long agenceId);

    @Query("select l from Laboratoire l where concat(l.nom, l.slug, l.email) like concat('%', :q,'%')")
    List<Laboratoire> findByQueryString(@Param("q") String q);

    @Query("select l from Laboratoire l where l.agence.id = :aId and concat(l.nom, l.slug, l.email) like concat('%', :q,'%')")
    List<Laboratoire> findByAgenceIdAndQueryString(@Param("aId") long agenceId, @Param("q") String q);
}