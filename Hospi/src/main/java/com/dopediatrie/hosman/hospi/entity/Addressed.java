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
public class Addressed {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private LocalDateTime date_op;
    private String specialite;
    private String med_ref;
    private String comments;
    private String institution;
    private String motif;
    private long medical_letter;
    private long medical_report;
    private String transport;
    @ManyToOne
    @JoinColumn(name = "hospit_id")
    private Hospit hospit;

}
