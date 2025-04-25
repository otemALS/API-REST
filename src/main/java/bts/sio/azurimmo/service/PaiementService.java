package bts.sio.azurimmo.service;

import bts.sio.azurimmo.model.Paiement;
import bts.sio.azurimmo.repository.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    public List<Paiement> findAllPaiements() {
        return paiementRepository.findAll();
    }

    public Optional<Paiement> findById(Long id) {
        return paiementRepository.findById(id);
    }

    public Paiement savePaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    public Paiement updatePaiement(Long id, Paiement paiementDetails) {
        return paiementRepository.findById(id).map(paiement -> {
            paiement.setMontant(paiementDetails.getMontant());
            paiement.setDatePaiement(paiementDetails.getDatePaiement());
            paiement.setContrat(paiementDetails.getContrat());
            return paiementRepository.save(paiement);
        }).orElse(null);
    }

    public boolean deletePaiement(Long id) {
        if (paiementRepository.existsById(id)) {
            paiementRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
