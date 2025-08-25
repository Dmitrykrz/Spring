package fr.diginamic.hello.Repositories;

import fr.diginamic.hello.Departement;
import fr.diginamic.hello.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface DAO pour l'entité Ville.
 * Utilise Spring Data JPA pour les opérations de base sur la base de données.
 */
@Repository
public interface VilleDao extends JpaRepository<Ville, Integer> {

    /**
     * Recherche une ville par son nom.
     * @param nom le nom de la ville.
     * @return un Optional contenant la ville si trouvée.
     */
    Optional<Ville> findByNomIgnoreCase(String nom);

    /**
     * Recherche les N premières villes d'un département, triées par nombre d'habitants décroissant.
     * @param departement le département.
     * @param n le nombre de villes à retourner.
     * @return une liste des villes trouvées.
     */
    @Query("SELECT v FROM Ville v WHERE v.departement = :departement ORDER BY v.nbHabitants DESC LIMIT :n")
    List<Ville> findTopNVillesByDepartement(Departement departement, int n);

    /**
     * Recherche les villes d'un département avec un nombre d'habitants compris entre min et max.
     * @param departement le département.
     * @param min le nombre d'habitants minimal.
     * @param max le nombre d'habitants maximal.
     * @return une liste des villes trouvées.
     */
    List<Ville> findByDepartementAndNbHabitantsBetween(Departement departement, int min, int max);
}