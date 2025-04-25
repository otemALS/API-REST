package bts.sio.azurimmo.controller;

import bts.sio.azurimmo.model.Appartement;
import bts.sio.azurimmo.model.Batiment;
import bts.sio.azurimmo.repository.AppartementRepository;
import bts.sio.azurimmo.service.AppartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appartements")
public class AppartementController {

    @Autowired
    private AppartementService appartementService;
    @Autowired
    private AppartementRepository appartementRepository;


    // Récupérer les appartements par ville
    @GetMapping("/ville/{ville}")
    public List<Appartement> findByVille(@PathVariable String ville) {
        return appartementService.findByVille(ville);
    }

    // Récupérer les appartements par ID de bâtiment
    @GetMapping("/batiment/{batimentId}")
    public List<Appartement> getAppartementsParBatiment(@PathVariable long batimentId) {
        return appartementService.getAppartementsParBatiment(batimentId);
    }

    // Créer un nouvel appartement
    @PostMapping("/")
    public ResponseEntity<Appartement> createAppartement(@RequestBody Appartement appartement) {
        if (appartement.getId() != null && appartement.getId() == 0) {
            appartement.setId(null);
        }

        // Vérifie que le bâtiment est bien récupéré avec l'ID fourni
        if (appartement.getBatiment() == null || appartement.getBatiment().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Appartement savedAppartement = appartementRepository.save(appartement);
        return ResponseEntity.ok(savedAppartement);
    }



    // Récupérer les appartements ayant une surface supérieure à un seuil
    @GetMapping("/surface/{surface}")
    public List<Appartement> findBySurfaceGreaterThan(@PathVariable float surface) {
        return appartementService.findBySurfaceGreaterThan(surface);
    }

    // Récupérer tous les appartements
    @GetMapping("/")
    public List<Appartement> getAllAppartements() {
        return appartementService.findAllAppartements();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Appartement> updateAppartement(@PathVariable Long id, @RequestBody Appartement updatedAppartement) {
        return appartementRepository.findById(id)
                .map(existing -> {
                    existing.setNumero(updatedAppartement.getNumero());
                    existing.setSurface(updatedAppartement.getSurface());
                    existing.setNbPieces(updatedAppartement.getNbPieces());
                    existing.setDescription(updatedAppartement.getDescription());
                    existing.setBatiment(updatedAppartement.getBatiment());
                    Appartement saved = appartementRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppartement(@PathVariable Long id) {
        if (!appartementRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        appartementRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
