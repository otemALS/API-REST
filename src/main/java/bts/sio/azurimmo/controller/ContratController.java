package bts.sio.azurimmo.controller;

import bts.sio.azurimmo.model.Contrat;
import bts.sio.azurimmo.repository.ContratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/api/contrats")
public class ContratController {

    @Autowired
    private ContratRepository contratRepository;

    // ✅ GET - Liste de tous les contrats
    @GetMapping
    public ResponseEntity<List<Contrat>> getAllContrats() {
        List<Contrat> contrats = contratRepository.findAll();
        return ResponseEntity.ok(contrats);
    }

    // ✅ GET - Un contrat par ID
    @GetMapping("/{id}")
    public ResponseEntity<Contrat> getContratById(@PathVariable Long id) {
        Optional<Contrat> contrat = contratRepository.findById(id);
        return contrat.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ POST - Créer un nouveau contrat
    @PostMapping
    public ResponseEntity<Contrat> createContrat(@RequestBody Contrat contrat) {
        if (contrat.getId() != null) {
            contrat.setId(null); // l'ID est généré automatiquement
        }
        Contrat saved = contratRepository.save(contrat);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ✅ PUT - Modifier un contrat
    @PutMapping("/{id}")
    public ResponseEntity<Contrat> updateContrat(@PathVariable Long id, @RequestBody Contrat contratDetails) {
        Optional<Contrat> optionalContrat = contratRepository.findById(id);
        if (optionalContrat.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Contrat contrat = optionalContrat.get();
        contrat.setDateEntree(contratDetails.getDateEntree());
        contrat.setDateSortie(contratDetails.getDateSortie());
        contrat.setMontantLoyer(contratDetails.getMontantLoyer());
        contrat.setMontantCharges(contratDetails.getMontantCharges());
        contrat.setStatut(contratDetails.getStatut());

        Contrat updated = contratRepository.save(contrat);
        return ResponseEntity.ok(updated);
    }

    // ✅ DELETE - Supprimer un contrat
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContrat(@PathVariable Long id) {
        if (!contratRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        contratRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
