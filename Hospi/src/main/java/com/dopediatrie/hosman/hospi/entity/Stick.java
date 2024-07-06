package com.dopediatrie.hosman.hospi.entity;

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
public class Stick {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String groupe;
    @Column(columnDefinition = "TEXT")
    private String value;

    @Override
    public String toString() {
        return "Stick{" +
                "id=" + id +
                ", groupe='" + groupe + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
