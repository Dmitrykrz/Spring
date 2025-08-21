
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

    /**
     * Contrôleur REST pour les données de recensement.
     * Gère les endpoints pour les requêtes sur les villes basées sur des critères de recensement.
     */
    @RestController
    @RequestMapping("/recensement")
    public class RecensementController {

        private final VilleService villeService;

        /**
         * Constructeur.
         * @param villeService le service pour les villes.
         */
        public RecensementController(VilleService villeService) {
            this.villeService = villeService;
        }

        /**
         * Extrait les N plus grandes villes d'un département.
         * @param nomDepartement le nom du département.
         * @param n le nombre de villes à retourner.
         * @return une liste de villes ou une réponse 404 si le département n'existe pas.
         * EXAMPLE
         *
         *     http://localhost:8080/recensement/top-villes/herault/1
         *
         *
         */
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

        /**
         * Extrait les villes d'un département avec une population entre min et max.
         * @param nomDepartement le nom du département.
         * @param min la population minimale.
         * @param max la population maximale.
         * @return une liste de villes.
         *
         * EXAMPLE
         *
         *      http://localhost:8080/recensement/villes-population/Herault/30000/350000
         */
        @GetMapping("/villes-population/{nomDepartement}/{min}/{max}")
        public ResponseEntity<List<Ville>> getVillesByPopulationAndDepartement(
                @PathVariable String nomDepartement,
                @PathVariable int min,
                @PathVariable int max) {

            List<Ville> villes = villeService.extractVillesByPopulationAndDepartement(nomDepartement, min, max);
            return ResponseEntity.ok(villes); // Always return 200 OK with the list (empty or not)
        }
    }