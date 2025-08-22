package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.Ville;
import fr.diginamic.hello.services.VilleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des villes.
 * Fournit des endpoints pour les opérations CRUD et les recherches de villes.
 */
@RestController
@RequestMapping("/villes")
public class VilleControleur {

    private final VilleService villeService;

    /**
     * Constructeur.
     * @param villeService le service pour les villes.
     */
    public VilleControleur(VilleService villeService) {
        this.villeService = villeService;
    }

    /**
     * Récupère toutes les villes.
     * @return une liste de toutes les villes.
     */
    @GetMapping
    public List<Ville> getVilles() {
        return villeService.extractVilles();
    }

    /**
     * Récupère une ville par son ID.
     * @param id l'ID de la ville.
     * @return la ville ou une réponse 404 si non trouvée.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<Ville> getVilleById(@PathVariable int id) {
        Ville v = villeService.extractVille(id);
        if (v != null) {
            return ResponseEntity.ok(v);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Récupère une ville par son nom.
     * @param nom le nom de la ville.
     * @return la ville ou une réponse 404 si non trouvée.
     */
    @GetMapping("/nom/{nom}")
    public ResponseEntity<Ville> getVilleByNom(@PathVariable String nom) {
        Ville v = villeService.extractVille(nom);
        if (v != null) {
            return ResponseEntity.ok(v);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Ajoute une nouvelle ville.
     * @param nouvelleVille la nouvelle ville à ajouter.
     * @param result le résultat de la validation.
     * @return une réponse indiquant le succès ou l'échec de l'opération.
     * Example:
     * {
     * "nom": "Lattes",
     * "nbHabitants": 3000,
     * "departement": {
     * "id": 1
     * }
     * }
     */
    @PostMapping
    public ResponseEntity<String> ajouterVille(@Valid @RequestBody Ville nouvelleVille, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Validation errors: " + errors);
        }

        Ville existing = villeService.extractVille(nouvelleVille.getNom());
        if (existing != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La ville existe déjà");
        }

        if (nouvelleVille.getDepartement() == null) {
            return ResponseEntity.badRequest().body("No department dans une Ville");
        }


        villeService.insertVille(nouvelleVille);
        return ResponseEntity.ok("Ville insérée avec succès");
    }

    /**
     * Modifie une ville existante par son ID.
     * @param id l'ID de la ville à modifier.
     * @param villeModifiee la ville avec les données modifiées.
     * @param result le résultat de la validation.
     * @return une réponse indiquant le succès ou l'échec de l'opération.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> modifierVille(@PathVariable int id, @Valid @RequestBody Ville villeModifiee, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Validation errors: " + errors);
        }

        Ville existing = villeService.extractVille(id);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ville id non trouvée");
        }

        villeService.modifierVille(id, villeModifiee);
        return ResponseEntity.ok("Ville modifiée avec succès");
    }

    /**
     * Supprime une ville par son ID.
     * @param id l'ID de la ville à supprimer.
     * @return une réponse indiquant le succès ou l'échec de l'opération.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerVille(@PathVariable int id) {
        Ville existing = villeService.extractVille(id);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ville id non trouvée");
        }

        villeService.supprimerVille(id);
        return ResponseEntity.ok("Ville supprimée");
    }
}