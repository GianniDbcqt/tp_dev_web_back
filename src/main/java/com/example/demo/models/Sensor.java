package com.example.demo.models;

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
    private Serre serre;


    private String Temperature;
    private String Humidite;
    private String windowState;
    private String WaterLevel;
    //private double valeur;
    private LocalDateTime dateReleve;



}
