package com.example.demo.controllers;

//import com.example.demo.models.Serre;

import com.example.demo.exceptions.NotFoundException;
import com.example.demo.models.Sensor;
import com.example.demo.models.Serre;
import com.example.demo.repositories.SerreRepository;
import com.example.demo.services.SerreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        return this.serreService.getSerre();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serre> getSerreById(@PathVariable Long id) {
        Optional<Serre> serre = serreService.findById(id);
        return serre.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Serre> createSerre(@RequestBody Serre serre) {
        try {
            return new ResponseEntity<>(this.serreService.updateSerre(serre), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSerre(@RequestBody Serre serre) {
        try {
            this.serreService.deleteSerre(serre.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   @PostMapping("/{id}")
    public ResponseEntity<Serre> updateSerre(@PathVariable Long id, @RequestBody Serre updatedSerre) {
        Optional<Serre> existingSerre = serreService.findById(id);

        if (existingSerre.isPresent()) {

            updatedSerre.setId(id); // Ensure ID is not changed from the request
            serreService.updateSerre(updatedSerre);
            return new ResponseEntity<>(updatedSerre, HttpStatus.OK);


        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/{serreId}/sensors")
    public ResponseEntity<Sensor> ajouterSensor(@PathVariable Long serreId, @RequestBody Sensor sensor) {

        try {
            Serre serreMiseAJour = serreService.ajouterSensorASerre(serreId, sensor);
            return new ResponseEntity<>(sensor, HttpStatus.CREATED);

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
