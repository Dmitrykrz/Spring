package fr.diginamic.hello.dao;

import fr.diginamic.hello.Departement;
import fr.diginamic.hello.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VilleDao extends JpaRepository<Ville, Integer> {
    Optional<Ville> findByNomIgnoreCase(String nom);

    @Query("SELECT v FROM Ville v WHERE v.departement = :departement ORDER BY v.nbHabitants DESC LIMIT :n")
    List<Ville> findTopNVillesByDepartement(Departement departement, int n);

    List<Ville> findByDepartementAndNbHabitantsBetween(Departement departement, int min, int max);
}