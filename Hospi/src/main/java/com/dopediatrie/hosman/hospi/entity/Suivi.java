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
public class Suivi {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String type;
    private long type_id;
    private double qte;
    private LocalDateTime apply_date;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    @Column(columnDefinition = "TEXT")
    private String extras;
    @ManyToOne
    @JoinColumn(name = "hospit_id")
    private Hospit hospit;

    @Override
    public String toString() {
        return "Suivi{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", type_id=" + type_id +
                ", qte=" + qte +
                ", apply_date=" + apply_date +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
