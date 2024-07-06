package com.dopediatrie.hosman.bm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Refused {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private LocalDateTime date_op;
    private String motif;
    private boolean has_decharge;
    private boolean has_ordonnance;
    private long ordonnce_id;
    private String comments;
    @OneToOne
    @JoinColumn(name = "consultation_id")
    @JsonIgnore
    private Consultation consultation;

}
