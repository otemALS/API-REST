package bts.sio.azurimmo.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bts.sio.azurimmo.model.Batiment;
import bts.sio.azurimmo.repository.BatimentRepository;

@CrossOrigin(origins = "http://localhost:3000") // Autorise le front sur le port 3000
@RestController
@RequestMapping("/api/batiments")
public class BatimentController {
    @Autowired
    private BatimentRepository batimentRepository;

    @GetMapping("/")
    public List<Batiment> getAllBatiments() {
        return batimentRepository.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Batiment> createBatiment(@RequestBody Batiment batiment) {
        // Si l'ID = 0, on le met à null pour forcer la création



if (batiment.getId() != null && batiment.getId() == 0) {
            batiment.setId(null);
        }
        Batiment savedBatiment = batimentRepository.save(batiment);
        return ResponseEntity.ok(savedBatiment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBatiment(@PathVariable Long id) {
        batimentRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }
}
