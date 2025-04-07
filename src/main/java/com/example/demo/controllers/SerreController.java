package com.example.demo.controllers;

//import com.example.demo.models.Serre;
import com.example.demo.repositories.SerreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


/*@RestController
@RequestMapping("/api/serres") // Define the base path for serre API requests
public class SerreController {

    @Autowired
    private SerreRepository serreRepository;



    @GetMapping
    public List<Serre> getAllSerres() {
        return serreRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serre> getSerreById(@PathVariable Long id) {
        Optional<Serre> serre = serreRepository.findById(id);
        return serre.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Serre> createSerre(@RequestBody Serre serre) {
        Serre savedSerre = serreRepository.save(serre);
        return new ResponseEntity<>(savedSerre, HttpStatus.CREATED);
    }

   /* @PutMapping("/{id}")
    public ResponseEntity<Serre> updateSerre(@PathVariable Long id, @RequestBody Serre updatedSerre) {
        Optional<Serre> existingSerre = serreRepository.findById(id);

        if (existingSerre.isPresent()) {

            updatedSerre.setId(id); // Ensure ID is not changed from the request
            serreRepository.save(updatedSerre);
            return new ResponseEntity<>(updatedSerre, HttpStatus.OK);


        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSerre(@PathVariable Long id) {
        serreRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
*/