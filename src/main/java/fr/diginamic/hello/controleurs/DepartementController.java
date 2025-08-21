package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.Departement;
import fr.diginamic.hello.services.DepartementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/departements")
public class DepartementController {

    private final DepartementService departementService;

    public DepartementController(DepartementService departementService) {
        this.departementService = departementService;
    }

    // ---------------- GET all ----------------
    @GetMapping
    public List<Departement> getDepartements() {
        return departementService.extractDepartements();
    }

    // ---------------- GET by ID ----------------
    @GetMapping("/id/{id}")
    public ResponseEntity<Departement> getDepartementById(@PathVariable int id) {
        Departement d = departementService.extractDepartement(id);
        if (d != null) {
            return ResponseEntity.ok(d);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // ---------------- GET by name ----------------
    @GetMapping("/nom/{nom}")
    public ResponseEntity<Departement> getDepartementByNom(@PathVariable String nom) {
        Departement d = departementService.extractDepartement(nom);
        if (d != null) {
            return ResponseEntity.ok(d);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * @param nouveauDepartement
     * @param result
     * @return
     *
     * http://localhost:8080/departements
     *
     * Example
     * {
     *   "nom": "Cote d Azur"
     *
     * }
     */

    // ---------------- POST ----------------
    @PostMapping
    public ResponseEntity<String> ajouterDepartement(@Valid @RequestBody Departement nouveauDepartement, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Validation errors: " + errors);
        }

        Departement existing = departementService.extractDepartement(nouveauDepartement.getNom());
        if (existing != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Le département existe déjà");
        }

        departementService.insertDepartement(nouveauDepartement);
        return ResponseEntity.ok("Département inséré avec succès");
    }

    // ---------------- PUT ----------------
    @PutMapping("/{id}")
    public ResponseEntity<String> modifierDepartement(@PathVariable int id, @Valid @RequestBody Departement departementModifie, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Validation errors: " + errors);
        }

        Departement existing = departementService.extractDepartement(id);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Département id non trouvée");
        }

        departementService.modifierDepartement(id, departementModifie);
        return ResponseEntity.ok("Département modifié avec succès");
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerDepartement(@PathVariable int id) {
        Departement existing = departementService.extractDepartement(id);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Département id non trouvée");
        }

        departementService.supprimerDepartement(id);
        return ResponseEntity.ok("Département supprimé");
    }
}
