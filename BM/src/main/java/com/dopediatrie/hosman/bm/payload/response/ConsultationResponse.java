package com.dopediatrie.hosman.bm.payload.response;

import com.dopediatrie.hosman.bm.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationResponse {
    private long id;
    private String reference;
    private LocalDateTime date_consultation;
    private String type;
    private String commentaire;
    private String hdm;
    private String patient_ref;
    private PatientResponse patient;
    private String secteur_code;
    private SecteurResponse secteur;
    private Long attente_num;
    private String consulteur_ref;
    private MedecinResponse consulteur;
    private AttenteResponse attente;
    private ConstanteResponse constante;
    private OrdonnanceResponse ordonnance;
    private List<MotifResponse> motifs;
    private List<ActeResponse> actes;
    private List<DiagnosticResponse> diagnostics;

    private AddressedResponse addressed;
    private TransferedResponse transfered;
    private RefusedResponse refused;
    private DecededResponse deceded;

    public ConsultationReportResponse toConsultationReport(){
        ConsultationReportResponse crr = new ConsultationReportResponse();
        crr.setNom(this.getPatient().getNom());
        crr.setPrenoms(this.getPatient().getPrenoms());
        crr.setSexe(String.valueOf(this.getPatient().getSexe()));
        crr.setSecteur(this.getSecteur().getLibelle());
        crr.setReference(this.getReference());
        crr.setDate_consultation(Date
                .from(this.getDate_consultation().atZone(ZoneId.systemDefault())
                        .toInstant()));
        if(this.getPatient() != null && this.getPatient().getDate_naissance() != null){
            crr.setDate_naissance(Date
                    .from(this.getPatient().getDate_naissance().atZone(ZoneId.systemDefault())
                            .toInstant()));
        }

        String medecin = "";
        if(this.getAttente_num() != null && this.getAttente_num() > 0){
            medecin = "Dr. "+this.getAttente().getMedecin_consulteur().getNom() + " "+this.getAttente().getMedecin_consulteur().getPrenoms();
        }
        crr.setMedecin(medecin);
        crr.setActes(Utils.convertActesListToString(this.getActes(), ","));
        if(this.getDiagnostics() != null && this.getDiagnostics().size() > 0)
            crr.setDiagnostics(Utils.convertDiagnosticsListToString(this.getDiagnostics(), ","));
        if(this.getMotifs() != null && this.getMotifs().size() > 0)
            crr.setMotifs(Utils.convertMotifsListToString(this.getMotifs(), ","));
        double montant_facture = 0;
        if(this.getAttente_num() != null && this.getAttente_num() > 0){
            montant_facture = 1;
        }
        crr.setMontant_facture((float) montant_facture);

        if(this.getConstante() != null){
            crr.setPoids(this.getConstante().getPoids());
            crr.setTaille(this.getConstante().getTaille());
            crr.setTension(this.getConstante().getTension());
            crr.setTemperature(this.getConstante().getTemperature());
            crr.setPoul(this.getConstante().getPoul());
            crr.setPerimetre_cranien(this.getConstante().getPerimetre_cranien());
            crr.setFrequence_respiratoire(this.getConstante().getFrequence_respiratoire());
        }

        return crr;
    }
}
