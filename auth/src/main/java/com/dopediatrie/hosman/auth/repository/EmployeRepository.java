package com.dopediatrie.hosman.auth.repository;

import com.dopediatrie.hosman.auth.entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeRepository extends JpaRepository<Employe,Long> {
}
