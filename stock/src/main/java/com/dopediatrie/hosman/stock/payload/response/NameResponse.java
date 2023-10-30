package com.dopediatrie.hosman.stock.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NameResponse {
    private long id;
    private String libelle;
    private String slug;

    @Override
    public String toString() {
        return "NameResponse{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", slug='" + slug + '\'' +
                '}';
    }
}