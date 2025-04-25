package bts.sio.azurimmo.service;

import bts.sio.azurimmo.model.Locataire;
import bts.sio.azurimmo.repository.LocataireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocataireService {

    @Autowired
    private LocataireRepository locataireRepository;

    public List<Locataire> getAllLocataires() {
        return locataireRepository.findAll();
    }

    public Optional<Locataire> getLocataireById(Long id) {
        return locataireRepository.findById(id);
    }

    public Locataire createLocataire(Locataire locataire) {
        return locataireRepository.save(locataire);
    }

    public Optional<Locataire> updateLocataire(Long id, Locataire locataireDetails) {
        return locataireRepository.findById(id).map(locataire -> {
            locataire.setNom(locataireDetails.getNom());
            locataire.setPrenom(locataireDetails.getPrenom());
            locataire.setDateN(locataireDetails.getDateN());
            locataire.setLieuN(locataireDetails.getLieuN());
            return locataireRepository.save(locataire);
        });
    }

    public boolean deleteLocataire(Long id) {
        if (!locataireRepository.existsById(id)) {
            return false;
        }
        locataireRepository.deleteById(id);
        return true;
    }
}
