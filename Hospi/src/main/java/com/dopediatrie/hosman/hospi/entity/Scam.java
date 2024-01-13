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
public class Scam {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private LocalDateTime date_op;
    private long ordonnance;
    private int decharge;
    private String comments;
    private String motif;
    @ManyToOne
    @JoinColumn(name = "hospit_id")
    private Hospit hospit;

}
