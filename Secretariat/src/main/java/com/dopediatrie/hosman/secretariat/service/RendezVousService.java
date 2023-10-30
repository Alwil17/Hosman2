package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.RendezVous;
import com.dopediatrie.hosman.secretariat.payload.request.RendezVousRequest;
import com.dopediatrie.hosman.secretariat.payload.response.RendezVousResponse;

import java.util.List;

public interface RendezVousService {
    List<RendezVous> getAllRendezVous();

    long addRendezVous(RendezVousRequest rendezVousRequest);

    void addRendezVous(List<RendezVousRequest> rendezVousRequests);

    RendezVousResponse getRendezVousById(long rendezVousId);

    void editRendezVous(RendezVousRequest rendezVousRequest, long rendezVousId);

    void cancelRendezVous(long rendezVousId);

    public void deleteRendezVousById(long rendezVousId);
}
