package com.example.demo.services;

import com.example.demo.exceptions.NotFoundException;
import com.example.demo.models.Sensor;
import com.example.demo.models.Serre;
import com.example.demo.repositories.SerreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class SerreService {

    private final SerreRepository serreRepository;


    @Transactional
    public Serre ajouterSensorASerre(Long serreId, Sensor sensor) {
        Optional<Serre> serreOptional = serreRepository.findById(serreId);

        if (serreOptional.isPresent()) {
            Serre serre = serreOptional.get();
            sensor.setSerre(serre);
            serre.getSensors().add(sensor);
            return serreRepository.save(serre);

        } else {
            throw new NotFoundException("Serre non trouvée avec l'ID : " + serreId);
        }
    }

    @Transactional
    public void supprimerSensorDeSerre(Long serreId, Long sensorId) {
        Optional<Serre> existingSerre = serreRepository.findById(serreId);
        if (existingSerre.isPresent()) {
            Serre serre = existingSerre.get();

            serre.getSensors().removeIf(sensor -> sensor.getId().equals(sensorId));
            serreRepository.save(serre);


        } else {
            throw new NotFoundException("Serre non trouvée avec l'ID : " + serreId);

        }
    }



    public List<Serre> getSerre() {
        return this.serreRepository.findAll();
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
}