
package com.example.demo.repositories;

import com.example.demo.models.Sensor;
import com.example.demo.models.Serre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {


    List<Sensor> findBySerre(Serre serre);


}