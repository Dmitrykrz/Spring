package fr.diginamic.hello.services;

import fr.diginamic.hello.Departement;
import fr.diginamic.hello.Ville;
import fr.diginamic.hello.dao.VilleDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class VilleService {

    private final VilleDao villeDao;

    private final DepartementService departementService;

    public VilleService(VilleDao villeDao, DepartementService departementService) {
        this.villeDao = villeDao;
        this.departementService = departementService;
    }

    public List<Ville> extractTopNVillesByDepartement(String nomDepartement, int n) {
        Departement departement = departementService.extractDepartement(nomDepartement);
        if (departement != null) {
            return villeDao.findTopNVillesByDepartement(departement, n);
        }
        return null;
    }

    public List<Ville> extractVillesByPopulationAndDepartement(String nomDepartement, int min, int max) {
        Departement departement = departementService.extractDepartement(nomDepartement);
        if (departement != null) {
            return villeDao.findByDepartementAndNbHabitantsBetween(departement, min, max);
        }
        return null;
    }

    // Liste toutes les villes
    public List<Ville> extractVilles() {
        return villeDao.findAll();
    }

    // Ville par id
    public Ville extractVille(int idVille) {
        return villeDao.findById(idVille).orElse(null);
    }

    // Ville par nom
    public Ville extractVille(String nom) {
        return villeDao.findByNomIgnoreCase(nom).orElse(null);
    }

    // Insère une ville et retourne toutes les villes
    public List<Ville> insertVille(Ville ville) {
        villeDao.save(ville);
        return villeDao.findAll();
    }

    // Modifie une ville par id et retourne toutes les villes
    public List<Ville> modifierVille(int idVille, Ville villeModifiee) {
        Optional<Ville> villeOptional = villeDao.findById(idVille);
        if (villeOptional.isPresent()) {
            Ville v = villeOptional.get();
            v.setNom(villeModifiee.getNom());
            v.setNbHabitants(villeModifiee.getNbHabitants());
            villeDao.save(v);
        }
        return villeDao.findAll();
    }

    // Supprime une ville par id et retourne toutes les villes
    public List<Ville> supprimerVille(int idVille) {
        villeDao.deleteById(idVille);
        return villeDao.findAll();
    }
}
