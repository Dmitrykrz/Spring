package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.Ville;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for /villes
 */
@RestController
@RequestMapping("/villes")
public class VilleControleur {


    private int nextId = 1;

    /**
     * this list acts as a database
     */
    private List<Ville> villes = new ArrayList<>();


    /**
     * @return JSON object of the database of the villes
     */
    @GetMapping
    public List<Ville> getVilles() {
        return villes;
    }


    /**
     * @param id Takes id
     * @return ville by id
     */

    @GetMapping("/{id}")
    public ResponseEntity<Ville> getVilleById(@PathVariable int id) {
        for (Ville v : villes) {
            if (v.getId() == id) {
                return ResponseEntity.ok(v);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    /**
     * @param nouvelleVille JSON object for ville
     * @return OK or FAIl based on existance of ville with same name
     * <p>
     * Json example for POST request on  http://localhost:8080/villes
     * {
     * "nom":"Paris",
     * "nbHabitants":9923399
     * }
     *
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

        for (Ville v : villes) {
            if (v.getNom().equalsIgnoreCase(nouvelleVille.getNom())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("La ville existe déjà");
            }
        }
        nouvelleVille.setId(nextId);
        villes.add(nouvelleVille);
        nextId++;
        return ResponseEntity.ok("Ville insérée avec succès");
    }

    /**
     * @param id            id of the ville
     * @param villeModifiee new JSON
     * @return OK or not OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> modifierVille( @PathVariable int id, @Valid @RequestBody Ville villeModifiee, BindingResult result) {

        if (result.hasErrors()) {
            String errors = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Validation errors: " + errors);
        }

        for (Ville v : villes) {
            if (v.getId() == id) {
                v.setNom(villeModifiee.getNom());
                v.setNbHabitants(villeModifiee.getNbHabitants());
                return ResponseEntity.ok("Ville modifiée");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ville id non trouvée");
    }

    /**
     * Deletes the ville. uses delete outside of loop to evade ConcurrentModificationException
     *
     * @param id of the ville
     * @return OK or Not Ok
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerVille(@PathVariable int id) {
        Ville villeToRemove = null;

        for (Ville v : villes) {
            if (v.getId() == id) {
                villeToRemove = v;
                break;
            }
        }

        if (villeToRemove != null) {
            villes.remove(villeToRemove);
            return ResponseEntity.ok("Ville supprimée");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ville id non trouvée");
        }


    }
}



