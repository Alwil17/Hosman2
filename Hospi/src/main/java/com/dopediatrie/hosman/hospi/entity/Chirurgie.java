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
public class Chirurgie {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private LocalDateTime date_op;
    private String med_type;
    private String med_ref;
    private double coef;
    private String comments;
    private String title;
    private int frais = 0;
    @ManyToOne
    @JoinColumn(name = "hospit_id")
    private Hospit hospit;

}
