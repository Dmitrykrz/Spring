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

    /**
     * Extrait tous les départements.
     * @return la liste de tous les départements.
     */
    public List<Departement> extractDepartements() {
        return entityManager.createQuery("SELECT d FROM Departement d", Departement.class)
                .getResultList();
    }

    /**
     * Extrait un département par son ID.
     * @param id ID du département.
     * @return le département ou null si non trouvé.
     */
    public Departement extractDepartement(int id) {
        return entityManager.find(Departement.class, id);
    }

    /**
     * Extrait un département par son nom.
     * @param nom nom du département.
     * @return le département ou null si non trouvé.
     */
    public Departement extractDepartement(String nom) {
        List<Departement> results = entityManager
                .createQuery("SELECT d FROM Departement d WHERE d.nom = :nom", Departement.class)
                .setParameter("nom", nom)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * Insère un département.
     * @param d le département à insérer.
     */
    public void insertDepartement(Departement d) {
        entityManager.persist(d);
    }

    /**
     * Modifie un département existant.
     * @param id ID du département à modifier.
     * @param departementModifie le département avec les données mises à jour.
     */
    public void modifierDepartement(int id, Departement departementModifie) {
        Departement existing = entityManager.find(Departement.class, id);
        if (existing != null) {
            existing.setNom(departementModifie.getNom());
            existing.setVilles(departementModifie.getVilles());
            entityManager.merge(existing);
        }
    }

    /**
     * Supprime un département par son ID.
     * @param id ID du département à supprimer.
     */
    public void supprimerDepartement(int id) {
        Departement existing = entityManager.find(Departement.class, id);
        if (existing != null) {
            entityManager.remove(existing);
        }
    }
}