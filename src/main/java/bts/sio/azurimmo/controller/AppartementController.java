package bts.sio.azurimmo.controller;

import bts.sio.azurimmo.model.Appartement;
import bts.sio.azurimmo.service.AppartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/api/appartements")
public class AppartementController {

    @Autowired
    private AppartementService appartementService;

    @GetMapping
    public ResponseEntity<List<Appartement>> getAllAppartements() {
        return ResponseEntity.ok(appartementService.findAllAppartements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appartement> getAppartementById(@PathVariable Long id) {
        return appartementService.getAppartementById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/batiment/{batimentId}")
    public ResponseEntity<List<Appartement>> getAppartementsByBatiment(@PathVariable Long batimentId) {
        return ResponseEntity.ok(appartementService.getAppartementsParBatiment(batimentId));
    }

    @GetMapping("/ville/{ville}")
    public ResponseEntity<List<Appartement>> getAppartementsByVille(@PathVariable String ville) {
        return ResponseEntity.ok(appartementService.findByVille(ville));
    }

    @GetMapping("/surface/{surface}")
    public ResponseEntity<List<Appartement>> getAppartementsBySurface(@PathVariable float surface) {
        return ResponseEntity.ok(appartementService.findBySurfaceGreaterThan(surface));
    }

    @PostMapping
    public ResponseEntity<Appartement> createAppartement(@RequestBody Appartement appartement) {
        Appartement saved = appartementService.createAppartement(appartement);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appartement> updateAppartement(@PathVariable Long id, @RequestBody Appartement updated) {
        return appartementService.updateAppartement(id, updated)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppartement(@PathVariable Long id) {
        if (appartementService.deleteAppartement(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
