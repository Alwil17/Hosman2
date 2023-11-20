package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.InterventionMotif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InterventionMotifRepository extends JpaRepository<InterventionMotif,Long> {

}