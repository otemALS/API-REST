package bts.sio.azurimmo.repository;

import bts.sio.azurimmo.model.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {
    // Pas besoin de méthode custom ici pour un CRUD basique
}
