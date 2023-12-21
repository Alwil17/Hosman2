package com.dopediatrie.hosman.hospi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Hospit {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String motif;
    private String diagnostic;
    private String hdm;
    private long patient_id;
    private long secteur_id;
    private long consultation_id;
    private LocalDateTime date_hospit;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    /*@ManyToOne
    @JoinColumn(name = "lit_id")
    private Lit lit;*/
    @OneToMany(mappedBy = "hospit")
    @JsonIgnore
    private List<Suivi> suivis;

    @Override
    public String toString() {
        return "Hospit{" +
                "id=" + id +
                ", motif='" + motif + '\'' +
                ", diagnostic='" + diagnostic + '\'' +
                ", hdm='" + hdm + '\'' +
                ", patient_id=" + patient_id +
                ", secteur_id=" + secteur_id +
                ", consultation_id=" + consultation_id +
                ", date_hospit=" + date_hospit +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
