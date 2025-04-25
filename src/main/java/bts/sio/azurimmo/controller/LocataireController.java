package bts.sio.azurimmo.controller;

import bts.sio.azurimmo.model.Locataire;
import bts.sio.azurimmo.service.LocataireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/api/locataires")
public class LocataireController {

    @Autowired
    private LocataireService locataireService;

    // GET /api/locataires
    @GetMapping
    public ResponseEntity<List<Locataire>> getAllLocataires() {
        return ResponseEntity.ok(locataireService.getAllLocataires());
    }

    // GET /api/locataires/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Locataire> getLocataireById(@PathVariable Long id) {
        return locataireService.getLocataireById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/locataires
    @PostMapping
    public ResponseEntity<Locataire> createLocataire(@RequestBody Locataire locataire) {
        // Nettoyage de l'ID au cas où
        if (locataire.getId() != null && locataire.getId() == 0) {
            locataire.setId(null);
        }

        Locataire saved = locataireService.createLocataire(locataire);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT /api/locataires/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Locataire> updateLocataire(@PathVariable Long id, @RequestBody Locataire details) {
        return locataireService.updateLocataire(id, details)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/locataires/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocataire(@PathVariable Long id) {
        if (locataireService.deleteLocataire(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
