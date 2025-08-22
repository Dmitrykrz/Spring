package fr.diginamic.hello.services;

import fr.diginamic.hello.Departement;
import fr.diginamic.hello.Ville;
import fr.diginamic.hello.dao.VilleDao;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class VilleService {

    private final VilleDao villeDao;
    //@Autowired              !!!!! NOT RECOMMENDED
    //VilleDao villeDao;      !!!!! NOT RECOMMENDED

    private final DepartementService departementService;
//
    /**
     * Constructeur.
     * @param villeDao le DAO pour les entités Ville.
     * @param departementService le service pour les départements.
     */
    public VilleService(VilleDao villeDao, DepartementService departementService) {
        this.villeDao = villeDao;
        this.departementService = departementService;
    }

    /**
     * Extrait les N plus grandes villes d'un département.
     * @param nomDepartement nom du département.
     * @param n nombre de villes à récupérer.
     * @return une liste des N villes, ou une liste vide si le département n'est pas trouvé.
     */
    public List<Ville> extractTopNVillesByDepartement(String nomDepartement, int n) {
        Departement departement = departementService.extractDepartement(nomDepartement);
        if (departement != null) {
            return villeDao.findTopNVillesByDepartement(departement, n);
        }
        return Collections.emptyList();
    }

    /**
     * Extrait les villes d'un département avec une population entre min et max.
     * @param nomDepartement nom du département.
     * @param min population minimale.
     * @param max population maximale.
     * @return une liste des villes, ou une liste vide si le département n'est pas trouvé.
     */
    public List<Ville> extractVillesByPopulationAndDepartement(String nomDepartement, int min, int max) {
        Departement departement = departementService.extractDepartement(nomDepartement);
        if (departement != null) {
            return villeDao.findByDepartementAndNbHabitantsBetween(departement, min, max);
        }
        return Collections.emptyList();
    }

    /**
     * Extrait toutes les villes.
     * @return la liste de toutes les villes.
     */
    public List<Ville> extractVilles() {
        return villeDao.findAll();
    }

    /**
     * Extrait une ville par son ID.
     * @param idVille ID de la ville.
     * @return la ville ou null si non trouvée.
     */
    public Ville extractVille(int idVille) {
        return villeDao.findById(idVille).orElse(null);
    }

    /**
     * Extrait une ville par son nom.
     * @param nom nom de la ville.
     * @return la ville ou null si non trouvée.
     */
    public Ville extractVille(String nom) {
        return villeDao.findByNomIgnoreCase(nom).orElse(null);
    }

    /**
     * Insère une ville.
     * @param ville la ville à insérer.
     * @return la liste de toutes les villes après l'insertion.
     */
    public List<Ville> insertVille(Ville ville) {
        villeDao.save(ville);
        return villeDao.findAll();
    }

    /**
     * Modifie une ville par son ID.
     * @param idVille ID de la ville à modifier.
     * @param villeModifiee la ville avec les données modifiées.
     * @return la liste de toutes les villes après la modification.
     */
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

    /**
     * Supprime une ville par son ID.
     * @param idVille ID de la ville à supprimer.
     * @return la liste de toutes les villes après la suppression.
     */
    public List<Ville> supprimerVille(int idVille) {
        villeDao.deleteById(idVille);
        return villeDao.findAll();
    }
}