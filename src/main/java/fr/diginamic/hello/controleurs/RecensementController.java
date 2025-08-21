
package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.Ville;
import fr.diginamic.hello.services.VilleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recensement")
public class RecensementController {

    private final VilleService villeService;

    public RecensementController(VilleService villeService) {
        this.villeService = villeService;
    }

    //http://localhost:8080/recensement/top-villes/herault/1

    @GetMapping("/top-villes/{nomDepartement}/{n}")
    public ResponseEntity<List<Ville>> getTopNVillesByDepartement(@PathVariable String nomDepartement, @PathVariable int n) {
        List<Ville> topVilles = villeService.extractTopNVillesByDepartement(nomDepartement, n);
        if (topVilles != null && !topVilles.isEmpty()) {
            return ResponseEntity.ok(topVilles);
        } else if (topVilles != null) {
            return ResponseEntity.ok(topVilles);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //http://localhost:8080/recensement/villes-population/Herault/30000/350000
    @GetMapping("/villes-population/{nomDepartement}/{min}/{max}")
    public ResponseEntity<List<Ville>> getVillesByPopulationAndDepartement(
            @PathVariable String nomDepartement,
            @PathVariable int min,
            @PathVariable int max) {

        List<Ville> villes = villeService.extractVillesByPopulationAndDepartement(nomDepartement, min, max);
        return ResponseEntity.ok(villes); // Always return 200 OK with the list (empty or not)
    }


}