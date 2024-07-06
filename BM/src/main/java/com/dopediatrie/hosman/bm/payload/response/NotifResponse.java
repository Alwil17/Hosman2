package com.dopediatrie.hosman.bm.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifResponse {
    private long id;
    private String titre;
    private String contenu;
    private LocalDateTime date_notif;

    @Override
    public String toString() {
        return "NotifResponse{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", contenu='" + contenu + '\'' +
                ", date_notif=" + date_notif +
                '}';
    }
}