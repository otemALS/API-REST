package bts.sio.azurimmo.service;

import bts.sio.azurimmo.model.Batiment;
import bts.sio.azurimmo.repository.BatimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatimentService {

    @Autowired
    private BatimentRepository batimentRepository;

    public List<Batiment> getAllBatiments() {
        return batimentRepository.findAll();
    }

    public Optional<Batiment> getBatimentById(Long id) {
        return batimentRepository.findById(id);
    }

    public Batiment createBatiment(Batiment batiment) {
        if (batiment.getId() != null && batiment.getId() == 0) {
            batiment.setId(null);
        }
        return batimentRepository.save(batiment);
    }

    public Optional<Batiment> updateBatiment(Long id, Batiment updated) {
        return batimentRepository.findById(id)
                .map(existing -> {
                    existing.setNom(updated.getNom());
                    existing.setAdresse(updated.getAdresse());
                    existing.setVille(updated.getVille());
                    return batimentRepository.save(existing);
                });
    }

    public boolean deleteBatiment(Long id) {
        if (!batimentRepository.existsById(id)) return false;
        batimentRepository.deleteById(id);
        return true;
    }
}
