package com.dopediatrie.hosman.auth.repository;

import com.dopediatrie.hosman.auth.entity.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartementRepository extends JpaRepository<Departement,Long> {
}
