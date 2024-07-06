package com.dopediatrie.hosman.bm.repository;

import com.dopediatrie.hosman.bm.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConsultationRepository extends JpaRepository<Consultation,Long> {

    @Override
    @Query("select c from Consultation c order by c.date_consultation desc")
    List<Consultation> findAll();

    @Query("select c from Consultation c where c.patient_ref = :patient order by c.date_consultation desc")
    List<Consultation> findAllByPatient_ref(@Param("patient") String patient);

    @Query("select c from Consultation c where c.reference = :ref")
    Optional<Consultation> findByRef(@Param("ref") String consultationRef);

    @Query("select c from Consultation c where c.date_consultation >= :datemin and c.date_consultation <= :datemax order by c.date_consultation desc")
    List<Consultation> findAllByDateRange(LocalDateTime datemin, LocalDateTime datemax);

    @Query("select c from Consultation c where c.date_consultation >= :datemin and c.date_consultation <= :datemax and c.secteur_code = :secteur order by c.date_consultation desc")
    List<Consultation> findAllByDateRangeAndSecteur(LocalDateTime datemin, LocalDateTime datemax, String secteur);

    @Query("select c from Consultation c where c.date_consultation >= :datemin and c.date_consultation <= :datemax and c.consulteur_ref = :docteur order by c.date_consultation desc")
    List<Consultation> findAllByDateRangeAndDocteur(LocalDateTime datemin, LocalDateTime datemax, String docteur);

    @Query("select c from Consultation c where c.date_consultation >= :datemin and c.date_consultation <= :datemax and c.secteur_code = :secteur and c.consulteur_ref = :docteur order by c.date_consultation desc")
    List<Consultation> findAllByDateRangeAndSecteurAndDocteur(LocalDateTime datemin, LocalDateTime datemax, String secteur, String docteur);

    @Query("select c from Consultation c JOIN ConsultationMotif cm ON c.id = cm.consultation.id where c.date_consultation >= :datemin and c.date_consultation <= :datemax and cm.motif.id = :motif order by c.date_consultation desc")
    List<Consultation> findAllByDateRangeAndMotif(LocalDateTime datemin, LocalDateTime datemax, Long motif);

    @Query("select c from Consultation c JOIN ConsultationDiagnostic cd ON c.id = cd.consultation.id where c.date_consultation >= :datemin and c.date_consultation <= :datemax and cd.diagnostic = :diagnostic order by c.date_consultation desc")
    List<Consultation> findAllByDateRangeAndDiagnostic(LocalDateTime datemin, LocalDateTime datemax, String diagnostic);

    @Query("select c from Consultation c JOIN ConsultationActe ca ON c.id = ca.consultation.id where c.date_consultation >= :datemin and c.date_consultation <= :datemax and ca.acte = :acte order by c.date_consultation desc")
    List<Consultation> findAllByDateRangeAndActe(LocalDateTime datemin, LocalDateTime datemax, String acte);

    @Query("select c from Consultation c JOIN ConsultationActe ca ON c.id = ca.consultation.id  JOIN ConsultationMotif cm ON c.id = cm.consultation.id where c.date_consultation >= :datemin and c.date_consultation <= :datemax and ca.acte = :acte and cm.motif.id = :motif order by c.date_consultation desc")
    List<Consultation> findAllByDateRangeAndActeAndMotif(LocalDateTime datemin, LocalDateTime datemax, String acte, Long motif);

    @Query("select c from Consultation c JOIN ConsultationActe ca ON c.id = ca.consultation.id  JOIN ConsultationDiagnostic cd ON c.id = cd.consultation.id where c.date_consultation >= :datemin and c.date_consultation <= :datemax and ca.acte = :acte and cd.diagnostic = :diagnostic order by c.date_consultation desc")
    List<Consultation> findAllByDateRangeAndActeAndDiagnostic(LocalDateTime datemin, LocalDateTime datemax, String acte, String diagnostic);

    @Query("select c from Consultation c JOIN ConsultationActe ca ON c.id = ca.consultation.id  JOIN ConsultationMotif cm ON c.id = cm.consultation.id JOIN ConsultationDiagnostic cd ON c.id = cd.consultation.id where c.date_consultation >= :datemin and c.date_consultation <= :datemax and ca.acte = :acte and cm.motif.id = :motif and cd.diagnostic = :diagnostic order by c.date_consultation desc")
    List<Consultation> findAllByDateRangeAndActeAndMotifAndDiagnostic(LocalDateTime datemin, LocalDateTime datemax, String acte, Long motif, String diagnostic);
}