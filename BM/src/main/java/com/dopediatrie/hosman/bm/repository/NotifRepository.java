package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Notif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotifRepository extends JpaRepository<Notif,Long> {

    @Query("select n from Notif n where n.produit.id = :pId")
    List<Notif> findAllByProduitId(@Param("pId") long produit_id);
}