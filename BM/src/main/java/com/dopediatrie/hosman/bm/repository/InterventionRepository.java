package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Intervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterventionRepository extends JpaRepository<Intervention,Long> {


}