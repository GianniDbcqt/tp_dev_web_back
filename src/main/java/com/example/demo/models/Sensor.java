
package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serre_id")
    @JsonIgnore
    private Serre serre;




    private String temperature;
    private String humidite;
    private String windowState;
    private String waterLevel;
    private LocalDateTime dateReleve;
    private Double valeur;
    private String Light;
    private String soilMoisture;

    private String type;

}
