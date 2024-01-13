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
public class MedExterne {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String type_op;
    private LocalDateTime date_op;
    private String comments;
    private String med_ref;
    @ManyToOne
    @JoinColumn(name = "hospit_id")
    private Hospit hospit;

}
