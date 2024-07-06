package com.dopediatrie.hosman.hospi.repository;

import com.dopediatrie.hosman.hospi.entity.Stick;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StickRepository extends JpaRepository<Stick,Long> {
    boolean existsByGroupe(String groupe);
    List<Stick> findByGroupeEquals(String groupe);

}