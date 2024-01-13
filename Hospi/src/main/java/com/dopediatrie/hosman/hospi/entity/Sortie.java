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
public class Sortie {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private LocalDateTime date_op;
    private String enceinte;
    @ManyToOne
    @JoinColumn(name = "hospit_id")
    private Hospit hospit;
    @OneToMany(mappedBy = "sortie")
    @JsonIgnore
    private List<SortieDiagnostic> diagnostics;
}
