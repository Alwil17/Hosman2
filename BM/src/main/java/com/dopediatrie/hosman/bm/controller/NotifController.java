package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Notif;
import com.dopediatrie.hosman.bm.payload.request.NotifRequest;
import com.dopediatrie.hosman.bm.payload.response.NotifResponse;
import com.dopediatrie.hosman.bm.service.NotifService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifs")
@RequiredArgsConstructor
@Log4j2
public class NotifController {

    private final NotifService notifService;

    @GetMapping
    public ResponseEntity<List<Notif>> getAllNotifs() {

        log.info("NotifController | getAllNotifs is called");
        return new ResponseEntity<>(notifService.getAllNotifs(), HttpStatus.OK);
    }

    @GetMapping("/produit/{id}")
    public ResponseEntity<List<Notif>> getAllNotifsByProduitId(@PathVariable("id") long id) {
        log.info("NotifController | getAllNotifsByProduitId is called");
        return new ResponseEntity<>(notifService.getAllNotifsByProduitId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addNotif(@RequestBody NotifRequest notifRequest) {

        log.info("NotifController | addNotif is called");

        log.info("NotifController | addNotif | notifRequest : " + notifRequest.toString());

        long notifId = notifService.addNotif(notifRequest);
        return new ResponseEntity<>(notifId, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> getNotifBySearch(@RequestParam("q") String q) {
        log.info("NotifController | getNotifBySearch is called");
        List<String> notifs = notifService.getNotifLike(q);
        return new ResponseEntity<>(notifs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotifResponse> getNotifById(@PathVariable("id") long notifId) {
        log.info("NotifController | getNotifById is called");
        log.info("NotifController | getNotifById | notifId : " + notifId);

        NotifResponse notifResponse
                = notifService.getNotifById(notifId);
        return new ResponseEntity<>(notifResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editNotif(@RequestBody NotifRequest notifRequest,
            @PathVariable("id") long notifId
    ) {

        log.info("NotifController | editNotif is called");

        log.info("NotifController | editNotif | notifId : " + notifId);

        notifService.editNotif(notifRequest, notifId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteNotifById(@PathVariable("id") long notifId) {
        notifService.deleteNotifById(notifId);
    }
}