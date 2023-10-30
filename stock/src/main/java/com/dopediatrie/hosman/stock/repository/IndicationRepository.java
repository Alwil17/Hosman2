package com.dopediatrie.hosman.stock.repository;

import com.dopediatrie.hosman.stock.entity.Indication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndicationRepository extends JpaRepository<Indication,Long> {

}