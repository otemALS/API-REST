package bts.sio.azurimmo.service;

import bts.sio.azurimmo.model.Appartement;
import bts.sio.azurimmo.repository.AppartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppartementService {

    @Autowired
    private AppartementRepository appartementRepository;

    public List<Appartement> findAllAppartements() {
        return appartementRepository.findAll();
    }

    public Optional<Appartement> getAppartementById(Long id) {
        return appartementRepository.findById(id);
    }

    public List<Appartement> findByVille(String ville) {
        return appartementRepository.findByBatiment_Ville(ville);
    }

    public List<Appartement> getAppartementsParBatiment(long batimentId) {
        return appartementRepository.findByBatiment_Id(batimentId);
    }

    public List<Appartement> findBySurfaceGreaterThan(float surface) {
        return appartementRepository.findBySurfaceGreaterThan(surface);
    }

    public Appartement createAppartement(Appartement appartement) {
        if (appartement.getId() != null && appartement.getId() == 0) {
            appartement.setId(null);
        }
        return appartementRepository.save(appartement);
    }

    public Optional<Appartement> updateAppartement(Long id, Appartement updated) {
        return appartementRepository.findById(id)
                .map(existing -> {
                    existing.setNumero(updated.getNumero());
                    existing.setSurface(updated.getSurface());
                    existing.setNbPieces(updated.getNbPieces());
                    existing.setDescription(updated.getDescription());
                    existing.setBatiment(updated.getBatiment());
                    return appartementRepository.save(existing);
                });
    }

    public boolean deleteAppartement(Long id) {
        if (!appartementRepository.existsById(id)) return false;
        appartementRepository.deleteById(id);
        return true;
    }
}
