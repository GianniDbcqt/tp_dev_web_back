// SerreController.java
package com.example.demo.controllers;

import com.example.demo.exceptions.NotFoundException;
import com.example.demo.models.Sensor;
import com.example.demo.models.Serre;
import com.example.demo.services.SerreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/serres")
@RequiredArgsConstructor
public class SerreController {

    private final SerreService serreService;

    @GetMapping
    public List<Serre> getAllSerres() {
        return serreService.getSerre();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serre> getSerreById(@PathVariable Long id) {
        return serreService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public Serre createSerre(@RequestBody Serre serre) {  // Simplification : retourne directement la Serre
        return serreService.updateSerre(serre); // updateSerre gère aussi la création
    }


    @PutMapping("/{id}") // Utilise PUT pour les mises à jour
    public ResponseEntity<Serre> updateSerre(@PathVariable Long id, @RequestBody Serre updatedSerre) {
        return serreService.findById(id)
                .map(existingSerre -> {
                    updatedSerre.setId(id);
                    return new ResponseEntity<>(serreService.updateSerre(updatedSerre), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{id}") // Utilise DELETE et l'ID pour la suppression
    public ResponseEntity<Void> deleteSerre(@PathVariable Long id) {
        try {
            serreService.deleteSerre(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping("/{serreId}/sensors")
    public ResponseEntity<Sensor> ajouterSensor(@PathVariable Long serreId, @RequestBody Sensor sensor) {
        try {
            Sensor createdSensor = serreService.ajouterSensorASerre(serreId, sensor);
            return new ResponseEntity<>(createdSensor, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{serreId}/sensors")
    public ResponseEntity<List<Sensor>> getSensorsBySerreId(@PathVariable Long serreId) {
        try {
            List<Sensor> sensors = serreService.getSensorsBySerreId(serreId);
            return new ResponseEntity<>(sensors, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{serreId}/sensors/{sensorId}")
    public ResponseEntity<Void> supprimerSensor(@PathVariable Long serreId, @PathVariable Long sensorId) {
        try {
            serreService.supprimerSensorDeSerre(serreId, sensorId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}