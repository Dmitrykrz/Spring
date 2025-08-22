package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.Departement;
import fr.diginamic.hello.Ville;
import fr.diginamic.hello.dto.VilleDto;
import fr.diginamic.hello.mappers.VilleMapper;
import fr.diginamic.hello.services.DepartementService;
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
    private final DepartementService departementService;

    /**
     * Constructeur.
     * @param villeService le service pour les villes.
     * @param departementService le service pour les départements.
     */
    public VilleControleur(VilleService villeService, DepartementService departementService) {
        this.villeService = villeService;
        this.departementService = departementService;
    }

    /**
     * Récupère toutes les villes.
     * @return une liste de toutes les villes.
     */
    @GetMapping
    public List<VilleDto> getVilles() {
        return villeService.extractVilles()
                .stream()
                .map(VilleMapper::toDto)
                .toList();
    }

    /**
     * Récupère une ville par son ID.
     * @param id l'ID de la ville.
     * @return la ville ou une réponse 404 si non trouvée.
     */
    @GetMapping("id/{id}")
    public ResponseEntity<Object> getVilleById(@PathVariable int id) {
        Ville ville = villeService.extractVille(id);
        if (ville == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ville id non trouvée");
        }
        return ResponseEntity.ok(VilleMapper.toDto(ville));
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
     * @param dto le DTO de la nouvelle ville à ajouter.
     * @param result le résultat de la validation.
     * @return une réponse indiquant le succès ou l'échec de l'opération.
     */
    @PostMapping
    public ResponseEntity<String> creerVille(@Valid @RequestBody VilleDto dto, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Validation errors: " + errors);
        }

        Departement departement = departementService.extractDepartement(dto.getIdDepartement());
        if (departement == null) {
            return ResponseEntity.badRequest().body("Departement non trouvée");
        }

        Ville newVille = VilleMapper.toEntity(dto, departement);
        villeService.insertVille(newVille);

        return ResponseEntity.ok("Ville créée avec succès");
    }

    /**
     * Modifie une ville existante par son ID.
     * @param id l'ID de la ville à modifier.
     * @param dto le DTO de la ville avec les données modifiées.
     * @param result le résultat de la validation.
     * @return une réponse indiquant le succès ou l'échec de l'opération.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> modifierVille(@PathVariable int id, @Valid @RequestBody VilleDto dto, BindingResult result) {
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

        existing.setNom(dto.getNom());
        existing.setNbHabitants(dto.getNbHabitants());
        // Pour l'instant, le département n'est pas modifié ici
        villeService.modifierVille(id, existing);

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
