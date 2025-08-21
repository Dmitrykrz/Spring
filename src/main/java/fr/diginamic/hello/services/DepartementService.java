package fr.diginamic.hello.services;

import fr.diginamic.hello.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DepartementService {

    @PersistenceContext
    private EntityManager entityManager;

    // ---------------- GET all ----------------
    public List<Departement> extractDepartements() {
        return entityManager.createQuery("SELECT d FROM Departement d", Departement.class)
                .getResultList();
    }

    // ---------------- GET by ID ----------------
    public Departement extractDepartement(int id) {
        return entityManager.find(Departement.class, id);
    }

    // ---------------- GET by name ----------------
    public Departement extractDepartement(String nom) {
        List<Departement> results = entityManager
                .createQuery("SELECT d FROM Departement d WHERE d.nom = :nom", Departement.class)
                .setParameter("nom", nom)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    // ---------------- INSERT ----------------
    public void insertDepartement(Departement d) {
        entityManager.persist(d);
    }

    // ---------------- UPDATE ----------------
    public void modifierDepartement(int id, Departement departementModifie) {
        Departement existing = entityManager.find(Departement.class, id);
        if (existing != null) {
            existing.setNom(departementModifie.getNom());
            existing.setVilles(departementModifie.getVilles());
            entityManager.merge(existing);
        }
    }

    // ---------------- DELETE ----------------
    public void supprimerDepartement(int id) {
        Departement existing = entityManager.find(Departement.class, id);
        if (existing != null) {
            entityManager.remove(existing);
        }
    }
}
