package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.Encaissement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EncaissementRepository extends JpaRepository<Encaissement,Long> {

}