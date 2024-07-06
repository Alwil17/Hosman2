package com.dopediatrie.hosman.hospi.payload.response;

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
    private String nom;
    private String slug;

    @Override
    public String toString() {
        return "NameResponse{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", slug='" + slug + '\'' +
                '}';
    }
}