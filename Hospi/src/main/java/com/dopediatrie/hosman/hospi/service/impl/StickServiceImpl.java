package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.entity.Stick;
import com.dopediatrie.hosman.hospi.exception.HospiCustomException;
import com.dopediatrie.hosman.hospi.payload.request.StickRequest;
import com.dopediatrie.hosman.hospi.payload.response.StickResponse;
import com.dopediatrie.hosman.hospi.repository.StickRepository;
import com.dopediatrie.hosman.hospi.service.StickService;
import com.dopediatrie.hosman.hospi.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class StickServiceImpl implements StickService {
    private final String NOT_FOUND = "STICK_NOT_FOUND";

    private final StickRepository stickRepository;

    @Override
    public List<Stick> getAllSticks() {
        return stickRepository.findAll();
    }

    @Override
    public long addStick(StickRequest stickRequest) {
        log.info("StickServiceImpl | addStick is called");
        Stick stick = Stick.builder()
                .groupe(stickRequest.getGroupe())
                .value(stickRequest.getValue())
                .build();
        stick = stickRepository.save(stick);

        log.info("StickServiceImpl | addStick | Stick Created");
        log.info("StickServiceImpl | addStick | Stick Id : " + stick.getId());
        return stick.getId();
    }

    @Override
    public void addStick(List<StickRequest> stickRequests) {
        log.info("StickServiceImpl | addStick is called");

        for (StickRequest stickRequest : stickRequests) {
            Stick stick = Stick.builder()
                    .groupe(stickRequest.getGroupe())
                    .value(stickRequest.getValue())
                    .build();
            stickRepository.save(stick);
        }

        log.info("StickServiceImpl | addStick | Stick Created");
    }

    @Override
    public StickResponse getStickById(long stickId) {
        log.info("StickServiceImpl | getStickById is called");
        log.info("StickServiceImpl | getStickById | Get the stick for stickId: {}", stickId);

        Stick stick
                = stickRepository.findById(stickId)
                .orElseThrow(
                        () -> new HospiCustomException("Stick with given Id not found", NOT_FOUND));

        StickResponse stickResponse = new StickResponse();

        copyProperties(stick, stickResponse);

        log.info("StickServiceImpl | getStickById | stickResponse :" + stickResponse.toString());

        return stickResponse;
    }

    @Override
    public void editStick(StickRequest stickRequest, long stickId) {
        log.info("StickServiceImpl | editStick is called");

        Stick stick
                = stickRepository.findById(stickId)
                .orElseThrow(() -> new HospiCustomException(
                        "Stick with given Id not found",
                        NOT_FOUND
                ));
        stick.setGroupe(stickRequest.getGroupe());
        stick.setValue(stickRequest.getValue());
        stickRepository.save(stick);

        log.info("StickServiceImpl | editStick | Stick Updated");
        log.info("StickServiceImpl | editStick | Stick Id : " + stick.getId());
    }

    @Override
    public void deleteStickById(long stickId) {
        log.info("Stick id: {}", stickId);

        if (!stickRepository.existsById(stickId)) {
            log.info("Im in this loop {}", !stickRepository.existsById(stickId));
            throw new HospiCustomException(
                    "Stick with given with Id: " + stickId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Stick with id: {}", stickId);
        stickRepository.deleteById(stickId);
    }

    @Override
    public List<Stick> getStickByGroupe(String groupe) {
        log.info("StickServiceImpl | getStickByNom is called");
        return stickRepository.findByGroupeEquals(groupe);
    }
}
