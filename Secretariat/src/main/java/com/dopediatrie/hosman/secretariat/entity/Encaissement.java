package com.dopediatrie.hosman.secretariat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Encaissement {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String provenance;
    private LocalDateTime date_encaissement;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "mode_encaissement",
            joinColumns = @JoinColumn(name = "encaissement_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "mode_payement_id", referencedColumnName = "id"))
    private List<ModePayement> mode_payements;
}
