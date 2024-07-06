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
    private String motif_libelle;
    private String diagnostic_code;
    private String hdm;
    private String patient_ref;
    private String secteur_code;
    private String consultation_ref;
    private String arrive;
    @Column(columnDefinition = "TEXT")
    private String extras;
    private int status = 0;
    private LocalDateTime date_hospit;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    /*@ManyToOne
    @JoinColumn(name = "lit_id")
    private Lit lit;*/
    @OneToMany(mappedBy = "hospit")
    @JsonIgnore
    private List<Suivi> suivis;
    @OneToMany(mappedBy = "hospit")
    @JsonIgnore
    private List<Suivi> addressed_list;
    @OneToMany(mappedBy = "hospit")
    @JsonIgnore
    private List<Suivi> transfused_list;
    @OneToMany(mappedBy = "hospit")
    @JsonIgnore
    private List<Suivi> deceded_list;
    @OneToMany(mappedBy = "hospit")
    @JsonIgnore
    private List<Suivi> chirurgies;
    @OneToMany(mappedBy = "hospit")
    @JsonIgnore
    private List<Suivi> medexternes;
    @OneToMany(mappedBy = "hospit")
    @JsonIgnore
    private List<Suivi> scams;
    @OneToMany(mappedBy = "hospit")
    @JsonIgnore
    private List<Suivi> sorties;

    @Override
    public String toString() {
        return "Hospit{" +
                "id=" + id +
                ", motif_libelle='" + motif_libelle + '\'' +
                ", diagnostic_code='" + diagnostic_code + '\'' +
                ", hdm='" + hdm + '\'' +
                ", patient_ref='" + patient_ref + '\'' +
                ", secteur_code='" + secteur_code + '\'' +
                ", consultation_ref='" + consultation_ref + '\'' +
                ", date_hospit=" + date_hospit +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
