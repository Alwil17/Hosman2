package com.dopediatrie.hosman.hospi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transfused {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private LocalDateTime date_op;
    private String provenance;
    private String donneur_ref;
    private String receveur_ref;
    private String hemoglobine;
    private String comments;
    private String motif;
    @ManyToOne
    @JoinColumn(name = "hospit_id")
    private Hospit hospit;

}
