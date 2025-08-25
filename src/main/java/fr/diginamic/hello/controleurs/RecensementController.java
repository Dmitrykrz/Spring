
    package fr.diginamic.hello.controleurs;

    import fr.diginamic.hello.Ville;
    import fr.diginamic.hello.dto.VilleDto;
    import fr.diginamic.hello.mappers.VilleMapper;
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
        @GetMapping("/top-n-villes-departement/{nomDepartement}/{n}")
        public ResponseEntity<List<VilleDto>> getTopNVillesByDepartement(@PathVariable String nomDepartement, @PathVariable int n) {
            List<Ville> topVilles = villeService.extractTopNVillesByDepartement(nomDepartement, n);
            if (topVilles != null && !topVilles.isEmpty()) {
                List<VilleDto> dtoList = topVilles.stream()
                        .map(VilleMapper::toDto)
                        .toList();
                return ResponseEntity.ok(dtoList);
            } else if (topVilles != null) {
                return ResponseEntity.ok(List.of()); // Retourne une liste vide si le département existe mais n'a pas de villes
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }

        @GetMapping("/top-n-villes/{n}")
        public ResponseEntity<List<VilleDto>> getTopNVilles(@PathVariable int n) {
            List<Ville> topVilles = villeService.extractTopNVilles(n);
            List<VilleDto> dtoList = topVilles.stream()
                    .map(VilleMapper::toDto)
                    .toList();
            return ResponseEntity.ok(dtoList);
        }

        @GetMapping("/villes-population/{nomDepartement}/{min}/{max}")
        public ResponseEntity<List<VilleDto>> getVillesByPopulationAndDepartement(
                @PathVariable String nomDepartement,
                @PathVariable int min,
                @PathVariable int max) {
            List<Ville> villes = villeService.extractVillesByPopulationAndDepartement(nomDepartement, min, max);
            List<VilleDto> dtoList = villes.stream()
                    .map(VilleMapper::toDto)
                    .toList();
            return ResponseEntity.ok(dtoList);
        }
    }