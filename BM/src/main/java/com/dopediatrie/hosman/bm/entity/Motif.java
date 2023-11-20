package com.dopediatrie.hosman.bm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Motif {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String libelle;
    private String slug;
    @ManyToMany(mappedBy = "motifs")
    @JsonIgnore
    private List<Intervention> interventions;
}
