package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.Ville;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/villes")
    public class VilleControleur {
    private int nextId = 1;




    //==================DATABASE======================
    private List<Ville> villes = new ArrayList<>();




    @GetMapping
    public List<Ville> getVilles() {
        return villes;
    }


    // http://localhost:8080/villes/2
    @GetMapping("/{id}")
    public ResponseEntity<Ville> getVilleById(@PathVariable int id) {
        for (Ville v : villes) {
            if (v.getId() == id) {
                return ResponseEntity.ok(v);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    // Json example for POST request on  http://localhost:8080/villes
    //  {
    //     "nom":"Paris",
    //          "nbHabitants":9923399
    //  }

    @PostMapping
    public ResponseEntity<String> ajouterVille(@RequestBody Ville nouvelleVille) {
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

    @PutMapping("/{id}")
    public ResponseEntity<String> modifierVille(@PathVariable int id, @RequestBody Ville villeModifiee) {
        for (Ville v : villes) {
            if (v.getId() == id) {
                v.setNom(villeModifiee.getNom());
                v.setNbHabitants(villeModifiee.getNbHabitants());
                return ResponseEntity.ok("Ville modifiée");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ville id non trouvée");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerVille(@PathVariable int id) {
        for (Ville v : villes) {
            if (v.getId() == id) {
                villes.remove(v);
                return ResponseEntity.ok("Ville supprimée");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ville id non trouvée");
    }



}



