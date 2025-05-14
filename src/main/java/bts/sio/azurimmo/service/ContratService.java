package bts.sio.azurimmo.service;

import bts.sio.azurimmo.model.Contrat;
import bts.sio.azurimmo.model.Appartement;
import bts.sio.azurimmo.model.Locataire;
import bts.sio.azurimmo.repository.ContratRepository;
import bts.sio.azurimmo.repository.AppartementRepository;
import bts.sio.azurimmo.repository.LocataireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContratService {

    @Autowired
    private ContratRepository contratRepository;

    @Autowired
    private LocataireRepository locataireRepository;

    @Autowired
    private AppartementRepository appartementRepository;

    // 🔹 Récupérer tous les contrats
    public List<Contrat> getAllContrats() {
        return contratRepository.findAll();
    }

    // 🔹 Récupérer un contrat par ID
    public Optional<Contrat> getContratById(Long id) {
        return contratRepository.findById(id);
    }

    // 🔹 Créer un nouveau contrat avec appartement et locataire complets
    public Contrat createContrat(Contrat contrat) {
        if (contrat.getId() != null) {
            contrat.setId(null); // ID auto-généré
        }

        // 🔸 Recharger les entités complètes à partir de leurs IDs
        Locataire loc = locataireRepository.findById(contrat.getLocataire().getId())
            .orElseThrow(() -> new RuntimeException("Locataire non trouvé"));
        Appartement appart = appartementRepository.findById(contrat.getAppartement().getId())
            .orElseThrow(() -> new RuntimeException("Appartement non trouvé"));

        contrat.setLocataire(loc);
        contrat.setAppartement(appart);

        return contratRepository.save(contrat);
    }

    // 🔹 Mettre à jour un contrat
    public Optional<Contrat> updateContrat(Long id, Contrat contratDetails) {
        return contratRepository.findById(id).map(contrat -> {
            contrat.setDateEntree(contratDetails.getDateEntree());
            contrat.setDateSortie(contratDetails.getDateSortie());
            contrat.setMontantLoyer(contratDetails.getMontantLoyer());
            contrat.setMontantCharges(contratDetails.getMontantCharges());
            contrat.setStatut(contratDetails.getStatut());

            Locataire loc = locataireRepository.findById(contratDetails.getLocataire().getId())
                .orElseThrow(() -> new RuntimeException("Locataire non trouvé"));
            Appartement appart = appartementRepository.findById(contratDetails.getAppartement().getId())
                .orElseThrow(() -> new RuntimeException("Appartement non trouvé"));

            contrat.setLocataire(loc);
            contrat.setAppartement(appart);

            return contratRepository.save(contrat);
        });
    }

    // 🔹 Supprimer un contrat
    public boolean deleteContrat(Long id) {
        if (!contratRepository.existsById(id)) {
            return false;
        }
        contratRepository.deleteById(id);
        return true;
    }
}
