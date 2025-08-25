package fr.diginamic.hello.services;

import fr.diginamic.hello.Departement;
import fr.diginamic.hello.Ville;
import fr.diginamic.hello.Repositories.VilleRepository;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class VilleService {

    private final VilleRepository villeRepository;
    //@Autowired              !!!!! NOT RECOMMENDED
    //VilleDao villeDao;      !!!!! NOT RECOMMENDED

    private final DepartementService departementService;
//
    /**
     * Constructeur.
     * @param villeRepository le DAO pour les entités Ville.
     * @param departementService le service pour les départements.
     */
    public VilleService(VilleRepository villeRepository, DepartementService departementService) {
        this.villeRepository = villeRepository;
        this.departementService = departementService;
    }

    /**
     * Extrait les N plus grandes villes d'un département.
     * @param nomDepartement nom du département.
     * @param n nombre de villes à récupérer.
     * @return une liste des N villes, ou une liste vide si le département n'est pas trouvé.
     */
    public List<Ville> extractTopNVillesByDepartement(String nomDepartement, int n) {
        return departementService.extractDepartement(nomDepartement)
                .map(departement -> villeRepository.findTopNVillesByDepartement(departement, n))
                .orElse(Collections.emptyList());
    }


    /**
     * Extrait les villes d'un département avec une population entre min et max.
     * @param nomDepartement nom du département.
     * @param min population minimale.
     * @param max population maximale.
     * @return une liste des villes, ou une liste vide si le département n'est pas trouvé.
     */
    public List<Ville> extractVillesByPopulationAndDepartement(String nomDepartement, int min, int max) {
        return departementService.extractDepartement(nomDepartement)
                .map(departement -> villeRepository.findByDepartementAndNbHabitantsBetween(departement, min, max))
                .orElse(Collections.emptyList());
    }

    public Optional<Ville> extractVilleOptional(String nom) {
        return villeRepository.findByNomIgnoreCase(nom);
    }
    /**
     * Extrait toutes les villes.
     * @return la liste de toutes les villes.
     */
    public List<Ville> extractVilles() {
        return villeRepository.findAll();
    }

    /**
     * Extrait une ville par son ID.
     * @param idVille ID de la ville.
     * @return la ville ou null si non trouvée.
     */
    public Ville extractVille(int idVille) {
        return villeRepository.findById(idVille).orElse(null);
    }

    /**
     * Extrait une ville par son nom.
     * @param nom nom de la ville.
     * @return la ville ou null si non trouvée.
     */
    public Ville extractVille(String nom) {
        return villeRepository.findByNomIgnoreCase(nom).orElse(null);
    }

    /**
     * Insère une ville.
     * @param ville la ville à insérer.
     * @return la liste de toutes les villes après l'insertion.
     */
    public List<Ville> insertVille(Ville ville) {
        villeRepository.save(ville);
        return villeRepository.findAll();
    }

    /**
     * Modifie une ville par son ID.
     * @param idVille ID de la ville à modifier.
     * @param villeModifiee la ville avec les données modifiées.
     * @return la liste de toutes les villes après la modification.
     */
    public List<Ville> modifierVille(int idVille, Ville villeModifiee) {
        Optional<Ville> villeOptional = villeRepository.findById(idVille);
        if (villeOptional.isPresent()) {
            Ville v = villeOptional.get();
            v.setNom(villeModifiee.getNom());
            v.setNbHabitants(villeModifiee.getNbHabitants());
            villeRepository.save(v);
        }
        return villeRepository.findAll();
    }

    /**
     * Supprime une ville par son ID.
     * @param idVille ID de la ville à supprimer.
     * @return la liste de toutes les villes après la suppression.
     */
    public List<Ville> supprimerVille(int idVille) {
        villeRepository.deleteById(idVille);
        return villeRepository.findAll();
    }
}