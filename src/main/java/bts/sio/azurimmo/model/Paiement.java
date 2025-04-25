package bts.sio.azurimmo.model;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "paiement")
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "datePaiement")
    private Date datePaiement;

    @ManyToOne
    @JoinColumn(name = "contrat_id")
    private Contrat contrat;

    // ----- Getters -----
    public Long getId() {
        return id;
    }

    public Double getMontant() {
        return montant;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public Contrat getContrat() {
        return contrat;
    }

    // ----- Setters -----
    public void setId(Long id) {
        this.id = id;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }
}
