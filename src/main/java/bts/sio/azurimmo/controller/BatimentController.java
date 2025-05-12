package bts.sio.azurimmo.controller;

import bts.sio.azurimmo.model.Batiment;
import bts.sio.azurimmo.service.BatimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/api/batiments")
public class BatimentController {

    @Autowired
    private BatimentService batimentService;

    // GET /api/batiments
    @GetMapping
    public ResponseEntity<List<Batiment>> getAllBatiments() {
        return ResponseEntity.ok(batimentService.getAllBatiments());
    }

    // GET /api/batiments/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Batiment> getBatimentById(@PathVariable Long id) {
        return batimentService.getBatimentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/batiments
    @PostMapping
    public ResponseEntity<Batiment> createBatiment(@RequestBody Batiment batiment) {
        Batiment saved = batimentService.createBatiment(batiment);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT /api/batiments/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Batiment> updateBatiment(@PathVariable Long id, @RequestBody Batiment updated) {
        return batimentService.updateBatiment(id, updated)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/batiments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBatiment(@PathVariable Long id) {
        if (batimentService.deleteBatiment(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
