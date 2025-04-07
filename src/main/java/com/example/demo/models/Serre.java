/*package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Serre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String location;
    private String plantType;

    // You might need to adjust this depending on your data model
    @ElementCollection // For storing map-like structures in JPA.
    @CollectionTable(name = "serre_capteurs", joinColumns = @JoinColumn(name = "serre_id"))
    @MapKeyColumn(name = "capteur_name")
    @Column(name = "capteur_value") // For storing the sensor data dynamically.
    private Map<String, String> capteurs = new HashMap<>();
}*/
