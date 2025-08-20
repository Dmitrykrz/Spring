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
    private List<Ville> villes = new ArrayList<>();

    @GetMapping
    public List<Ville> getVilles() {
        return villes;
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

        villes.add(nouvelleVille);
        return ResponseEntity.ok("Ville insérée avec succès");
    }



}



