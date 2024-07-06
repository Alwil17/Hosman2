package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous,Long> {

    @Query("select r from RendezVous r JOIN Etat e where e.slug <> 'annulee'")
    List<RendezVous> findAll();

    @Query("select r from RendezVous r JOIN Etat e where e.slug <> 'annulee' and r.medecin_ref = :medecin or r.intervenant_ref = :medecin")
    List<RendezVous> findByMedecin(@Param("medecin") String medecin);

    @Query("select r from RendezVous r JOIN Etat e where e.slug <> 'annulee' and r.date_rdv >= :datemin and r.date_rdv <= :datemax")
    List<RendezVous> groupByMedecinAndDateminAndDatemax(@Param("datemin") LocalDateTime datemin, @Param("datemax") LocalDateTime datemax);

    @Query("select r from RendezVous r JOIN Etat e where e.slug <> 'annulee' and r.date_rdv >= :datemin and r.date_rdv <= :datemax and r.medecin_ref = :matricule")
    List<RendezVous> groupByMedecinAndDateminAndDatemaxAndMatricule(@Param("datemin") LocalDateTime datemin, @Param("datemax") LocalDateTime datemax, @Param("matricule") String matricule);

    @Query("select r from RendezVous r JOIN Etat e where e.slug <> 'annulee' and r.date_rdv >= :datemin and r.date_rdv <= :datemax")
    List<RendezVous> findByDateMinAndMax(@Param("datemin") LocalDateTime datemin, @Param("datemax") LocalDateTime datemax);
}