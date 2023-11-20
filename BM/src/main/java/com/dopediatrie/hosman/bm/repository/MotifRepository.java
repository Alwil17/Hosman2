package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Motif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MotifRepository extends JpaRepository<Motif,Long> {

    @Query("SELECT m from Motif m where m.libelle like concat('%',:libelle,'%')")
    List<Motif> findByLibelleLike(@Param("libelle") String libelle);
}