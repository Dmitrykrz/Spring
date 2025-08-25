package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.Departement;
import fr.diginamic.hello.dto.DepartementDto;
import fr.diginamic.hello.mappers.DepartementMapper;
import fr.diginamic.hello.services.DepartementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des départements.
 * Gère les endpoints pour les opérations CRUD et les recherches de départements.
 */
@RestController
@RequestMapping("/departements")
public class DepartementController {

    private final DepartementService departementService;

    /**
     * Constructeur.
     * @param departementService le service pour les départements.
     */
    public DepartementController(DepartementService departementService) {
        this.departementService = departementService;
    }



    /**
     * Récupère un département par son ID.
     * @param id l'ID du département.
     * @return le département ou une réponse 404 si non trouvé.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getDepartementById(@PathVariable int id) {
        return departementService.extractDepartement(id)
                .<ResponseEntity<Object>>map(d -> ResponseEntity.ok(DepartementMapper.toDto(d)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Département id non trouvée"));

    }


    /**
     * Récupère un département par son nom.
     * @param nom le nom du département.
     * @return le département ou une réponse 404 si non trouvé.
     */
    @GetMapping
    public List<DepartementDto> getDepartements() {
        return departementService.extractDepartements()
                .stream()
                .map(DepartementMapper::toDto)
                .toList();
    }


    /**
     * Ajoute un nouveau département.
     * @param nouveauDepartement le département à ajouter.
     * @param result le résultat de la validation.
     * @return une réponse indiquant le succès ou l'échec de l'opération.
     */
    @PostMapping
    public ResponseEntity<String> ajouterDepartement(@Valid @RequestBody Departement nouveauDepartement, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Validation errors: " + errors);
        }

        boolean exists = departementService.extractDepartement(nouveauDepartement.getNom()).isPresent() ||
                departementService.extractDepartementByCode(nouveauDepartement.getCode()).isPresent();

        if (exists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Le département existe déjà");
        }

        departementService.insertDepartement(nouveauDepartement);
        return ResponseEntity.ok("Département inséré avec succès");
    }
    /**
     * Modifie un département existant par son ID.
     * @param id l'ID du département à modifier.
     * @param departementModifie le département avec les données modifiées.
     * @param result le résultat de la validation.
     * @return une réponse indiquant le succès ou l'échec de l'opération.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> modifierDepartement(@PathVariable int id,
                                                      @Valid @RequestBody DepartementDto departementModifie,
                                                      BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Validation errors: " + errors);
        }

        Optional<Departement> existingOpt = departementService.extractDepartement(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Département id non trouvée");
        }

        Departement existing = existingOpt.get();
        // Mettre à jour seulement les champs nécessaires (ici, le nom et le code)
        existing.setNom(departementModifie.getNom());
        existing.setCode(departementModifie.getCode());
        departementService.modifierDepartement(id, existing);

        return ResponseEntity.ok("Département modifié avec succès");
    }



    /**
     * Supprime un département par son ID.
     * @param id l'ID du département à supprimer.
     * @return une réponse indiquant le succès ou l'échec de l'opération.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerDepartement(@PathVariable int id) {
        Optional<Departement> existingOpt = departementService.extractDepartement(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Département id non trouvée");
        }

        departementService.supprimerDepartement(id);
        return ResponseEntity.ok("Département supprimé");
    }

}