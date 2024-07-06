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
public class Deceded {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private LocalDateTime date_op;
    private boolean reanimation = false;
    private boolean after_out = false;
    private String comments;
    private String motif;
    @OneToOne
    @JoinColumn(name = "consultation_id")
    @JsonIgnore
    private Consultation consultation;

}
