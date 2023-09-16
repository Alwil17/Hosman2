package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Acte;
import com.dopediatrie.hosman.secretariat.entity.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GroupeRepository extends JpaRepository<Groupe,Long> {
    @Override
    @Query("select g from Groupe g order by g.position asc")
    List<Groupe> findAll();

    Optional<Groupe> findByCodeEquals(String code);
}