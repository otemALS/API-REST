package bts.sio.azurimmo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import bts.sio.azurimmo.model.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
}
