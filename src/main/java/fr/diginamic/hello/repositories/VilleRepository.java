package fr.diginamic.hello.repositories;

import fr.diginamic.hello.Departement;
import fr.diginamic.hello.Ville;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface VilleRepository extends JpaRepository<Ville, Integer> {

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

    /**
     * Recherche les N plus grandes villes de France.
     * @param pageable objet Pageable pour la pagination et le tri.
     * @return une liste des villes trouvées.
     */
    List<Ville> findByOrderByNbHabitantsDesc(Pageable pageable);


    /**
     * Recherche toutes les villes dont le nom commence par une chaîne de caractères donnée.
     * @param nomFragment la chaîne de caractères.
     * @return une liste des villes trouvées.
     */
    List<Ville> findByNomStartingWithIgnoreCase(String nomFragment);

    /**
     * Recherche toutes les villes dont la population est supérieure à 'min', triées par population descendante.
     * @param min la population minimale.
     * @return une liste des villes triées par population.
     */
    List<Ville> findByNbHabitantsGreaterThanOrderByNbHabitantsDesc(int min);

    /**
     * Recherche toutes les villes dont la population est comprise entre 'min' et 'max', triées par population descendante.
     * @param min la population minimale.
     * @param max la population maximale.
     * @return une liste des villes triées par population.
     */
    List<Ville> findByNbHabitantsBetweenOrderByNbHabitantsDesc(int min, int max);

    /**
     * Recherche toutes les villes d'un département donné dont la population est supérieure à 'min',
     * triées par population descendante.
     * @param departement l'entité Département.
     * @param min la population minimale.
     * @return une liste des villes triées par population.
     */
    List<Ville> findByDepartementAndNbHabitantsGreaterThanOrderByNbHabitantsDesc(Departement departement, int min);

    /**
     * Recherche toutes les villes d'un département donné dont la population est comprise entre 'min' et 'max',
     * triées par population descendante.
     * @param departement l'entité Département.
     * @param min la population minimale.
     * @param max la population maximale.
     * @return une liste des villes triées par population.
     */
    List<Ville> findByDepartementAndNbHabitantsBetweenOrderByNbHabitantsDesc(Departement departement, int min, int max);
}