package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.RendezVous;
import com.dopediatrie.hosman.secretariat.payload.request.RendezVousRequest;
import com.dopediatrie.hosman.secretariat.payload.response.RendezVousResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface RendezVousService {
    List<RendezVous> getAllRendezVous();

    long addRendezVous(RendezVousRequest rendezVousRequest);

    void addRendezVous(List<RendezVousRequest> rendezVousRequests);

    RendezVousResponse getRendezVousById(long rendezVousId);

    void editRendezVous(RendezVousRequest rendezVousRequest, long rendezVousId);

    void cancelRendezVous(long rendezVousId);

    public void deleteRendezVousById(long rendezVousId);

    List<RendezVousResponse> getRendezVousByMedecin(String medRef);

    List<String> getAllObjets();

    Map<String, List<RendezVous>> getRendezVousByMedecinAndPeriod(LocalDateTime dateDebut, LocalDateTime dateFin);

    Map<String, List<RendezVous>> getRendezVousByMedecinMatAndPeriod(LocalDateTime dateDebut, LocalDateTime dateFin, String matricule);

    List<RendezVousResponse> getRendezVousByPeriod(LocalDateTime dateDebut, LocalDateTime dateFin);
}
