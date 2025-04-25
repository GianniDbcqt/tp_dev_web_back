package com.example.demo.services;

import com.example.demo.exceptions.NotFoundException;
import com.example.demo.models.Sensor;
import com.example.demo.models.Serre;
import com.example.demo.repositories.SensorRepository;
import com.example.demo.repositories.SerreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class SerreService {

    private final SerreRepository serreRepository;
    private final SensorRepository sensorRepository;

    @Transactional
    public Sensor ajouterSensorASerre(Long serreId, Sensor sensor) {
        Serre serre = serreRepository.findById(serreId)
                .orElseThrow(() -> new NotFoundException("Serre non trouvée avec l'ID : " + serreId));

        if (serre.getSensors().stream().anyMatch(s -> s.getType().equals(sensor.getType()))) {
            throw new IllegalArgumentException("Un capteur de ce type existe déjà pour cette serre.");
        }

        sensor.setSerre(serre);
        sensor.setDateReleve(LocalDateTime.now());

        // Appeler directement sensorRepository.save() pour la création
        return sensorRepository.save(sensor);
    }

    @Transactional
    public void supprimerSensorDeSerre(Long serreId, Long sensorId) {
        Optional<Serre> existingSerre = serreRepository.findById(serreId);
        if (existingSerre.isPresent()) {
            Optional<Sensor> existingSensor = sensorRepository.findById(sensorId);
            if (existingSensor.isPresent()) {
                sensorRepository.delete(existingSensor.get());
            } else {
                throw new NotFoundException("Capteur non trouvé avec l'ID : " + sensorId);
            }

        } else {
            throw new NotFoundException("Serre non trouvée avec l'ID : " + serreId);
        }
    }

    public List<Serre> getSerre() {
        return this.serreRepository.findAll();
    }

    @Transactional
    public Sensor updateSensor(Long serreId, Long sensorId, Sensor updatedSensor) {
        Sensor sensor;
            sensor = sensorRepository.findById(sensorId)
                    .orElseThrow(() -> new NotFoundException("Capteur non trouvé avec l'ID : " + sensorId));

            switch (updatedSensor.getType()) {
                case "temperature":
                    sensor.setTemperature(String.valueOf(updatedSensor.getValeur()));
                    break;
                case "humidity":
                    sensor.setHumidite(String.valueOf(updatedSensor.getValeur()));
                    break;
                case "waterLevel":
                    sensor.setWaterLevel(String.valueOf(updatedSensor.getValeur()));
                    break;
                case "windowState":
                    sensor.setWindowState(String.valueOf(updatedSensor.getValeur()));
                    break;
                case "light":
                    sensor.setLight(String.valueOf(updatedSensor.getValeur()));
                    break;
                case "soilMoisture":
                    sensor.setSoilMoisture(String.valueOf(updatedSensor.getValeur()));
                    break;
                default:
                    throw new IllegalArgumentException("Type de capteur inconnu : " + updatedSensor.getType());
            }


        sensor.setDateReleve(LocalDateTime.now());
        return sensorRepository.save(sensor);
    }


    @Transactional
    public Serre updateSerre(Serre serre) throws NotFoundException {
        if (serre.getId() == null) {
            serre = this.serreRepository.save(serre);
            return serre;
        } else {
            Optional<Serre> found = this.serreRepository.findById(serre.getId());
            if (found.isEmpty()) {
                log.error("Serre with id {} not found", serre.getId());
                throw new NotFoundException("Can't update serre", "Can't find Serre with id : " + serre.getId());
            }

            Serre existingSerre = found.get();
            existingSerre.setNom(serre.getNom());
            existingSerre.setLocation(serre.getLocation());
            existingSerre.setPlantType(serre.getPlantType());
            this.serreRepository.save(existingSerre);

            return existingSerre;
        }
    }



    public Optional<Serre> findById(Long id) {
        return this.serreRepository.findById(id);
    }


    public void deleteSerre(Long serreId) throws NotFoundException {
        Optional<Serre> existingSerre = serreRepository.findById(serreId);
        if (existingSerre.isPresent()) {
            serreRepository.delete(existingSerre.get());
        } else {
            throw new NotFoundException("Serre non trouvée avec l'ID : " + serreId);
        }
    }

    public List<Sensor> getSensorsBySerreId(Long serreId) {
        Serre serre = serreRepository.findById(serreId)
                .orElseThrow(() -> new NotFoundException("Serre non trouvée avec l'ID : " + serreId));
        return sensorRepository.findBySerre(serre);
    }
}