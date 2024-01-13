package com.dopediatrie.hosman.secretariat.repository;

import com.dopediatrie.hosman.secretariat.entity.PEC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PECRepository extends JpaRepository<PEC,Long> {

    @Query("SELECT p from PEC p JOIN p.facture f where f.date_facture >= :datemin and f.date_facture <= :datemax")
    List<PEC> findAllByDateMinAndMax(@Param("datemin") LocalDateTime dateDebut, @Param("datemax") LocalDateTime dateFin);

    @Query("SELECT p from PEC p JOIN p.facture f JOIN p.assurance a where f.date_facture >= :datemin and f.date_facture <= :datemax and a.slug = :assur")
    List<PEC> findAllByDateMinAndMaxAndAssur(@Param("datemin") LocalDateTime dateDebut, @Param("datemax") LocalDateTime dateFin, @Param("assur") String assur_slug);

    @Query("SELECT p from PEC p JOIN p.facture f JOIN p.assurance a JOIN a.type_assurance t where f.date_facture >= :datemin and f.date_facture <= :datemax and t.slug = :type")
    List<PEC> findAllByDateMinAndMaxAndType(@Param("datemin") LocalDateTime dateDebut, @Param("datemax") LocalDateTime dateFin, @Param("type") String assur_type);

    @Query("SELECT p from PEC p JOIN p.facture f JOIN p.assurance a JOIN a.type_assurance t where f.date_facture >= :datemin and f.date_facture <= :datemax and t.slug = :type and a.slug = :assur")
    List<PEC> findAllByDateMinAndMaxAndTypeAndSlug(@Param("datemin") LocalDateTime dateDebut, @Param("datemax") LocalDateTime dateFin, @Param("type") String assur_type, @Param("assur") String assur_slug);
}