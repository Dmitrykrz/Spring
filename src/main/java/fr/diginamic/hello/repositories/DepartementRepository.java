package fr.diginamic.hello.repositories;

import fr.diginamic.hello.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Integer> {

    /**
     * Recherche un département par son nom (insensible à la casse).
     * @param nom le nom du département.
     * @return un Optional contenant le département si trouvé.
     */
    Optional<Departement> findByNomIgnoreCase(String nom);

    /**
     * Recherche les départements dont le nom contient une chaîne (insensible à la casse).
     * @param fragment partie du nom.
     * @return liste des départements trouvés.
     */
    List<Departement> findByNomContainingIgnoreCase(String fragment);
}
