package com.dopediatrie.hosman.bm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
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
public class Ordonnance {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String diagnostic;
    private String reference;
    private String patient_ref;
    @Max(value = 50)
    private String indicateur1;
    @Max(value = 50)
    private String indicateur2;
    private boolean stocked = false;
    private boolean prepositionned = false;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ordonnance_prescriptions",
            joinColumns = @JoinColumn(name = "ordonnance_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "prescription_id", referencedColumnName = "id"))
    private List<Prescription> prescriptions;

    @OneToOne(mappedBy = "ordonnance")
    @JsonIgnore
    private Consultation consultation;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
