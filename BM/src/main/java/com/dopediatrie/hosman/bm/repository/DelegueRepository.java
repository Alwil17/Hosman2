package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Delegue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DelegueRepository extends JpaRepository<Delegue,Long> {

    boolean existsByNomAndPrenomsAndTel1AndTel2(String nom, String prenoms, String tel1, String tel2);

    Optional<Delegue> findByNomAndPrenomsAndTel1AndTel2(String nom, String prenoms, String tel1, String tel2);

    @Query("select d from Delegue d join d.laboratoire l join l.agence a where a.id = :aId")
    List<Delegue> findDelegueByAgenceId(@Param("aId") long agenceId);

    @Query("select d from Delegue d join d.laboratoire l join l.agence a where a.id = :aId and concat(d.nom, d.prenoms, d.email) like concat('%', :q,'%')")
    List<Delegue> findDelegueByAgenceIdAndQueryString(@Param("aId") long agenceId, @Param("q") String q);

    @Query("select d from Delegue d where d.laboratoire.id = :lId")
    List<Delegue> findDelegueByLaboId(@Param("lId") long laboId);

    @Query("select d from Delegue d where d.laboratoire.id = :lId and concat(d.nom, d.prenoms, d.email) like concat('%', :q,'%')")
    List<Delegue> findDelegueByLaboIdAndQueryString(@Param("lId") long laboId, @Param("q") String q);

    @Query("select d from Delegue d where concat(d.nom, d.prenoms, d.email) like concat('%', :q,'%')")
    List<Delegue> findDelegueByQueryString(@Param("q") String q);
}