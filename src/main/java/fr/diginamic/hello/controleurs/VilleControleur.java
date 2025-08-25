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
import java.util.Optional;
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

        Optional<Departement> departementOpt = departementService.extractDepartement(dto.getIdDepartement());
        if (departementOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Departement non trouvée");
        }

        Departement departement = departementOpt.get();

        // Check if a Ville with this name already exists
        Optional<Ville> existingVille = villeService.extractVilleOptional(dto.getNom());
        if (existingVille.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ville avec ce nom existe déjà");
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

    /**
     * Récupère toutes les villes dont le nom commence par un fragment donné.
     * Endpoint : GET /villes/search/by-name-fragment/{fragment}
     * @param fragment le fragment de nom.
     * @return une liste des villes trouvées.
     */
    @GetMapping("/search/by-name-fragment/{fragment}")
    public ResponseEntity<List<VilleDto>> getVillesByNameFragment(@PathVariable String fragment) {
        List<Ville> villes = villeService.extractVillesByNameFragment(fragment);
        List<VilleDto> dtoList = villes.stream()
                .map(VilleMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    /**
     * Récupère toutes les villes dont la population est supérieure à une valeur minimale.
     * Endpoint : GET /villes/search/population/greater-than/{min}
     * @param min la population minimale.
     * @return une liste des villes trouvées, triées par population descendante.
     */
    @GetMapping("/search/population/greater-than/{min}")
    public ResponseEntity<List<VilleDto>> getVillesByPopulationGreaterThan(@PathVariable int min) {
        List<Ville> villes = villeService.extractVillesByPopulationGreaterThan(min);
        List<VilleDto> dtoList = villes.stream()
                .map(VilleMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    /**
     * Récupère toutes les villes dont la population est comprise entre min et max.
     * Endpoint : GET /villes/search/population/between/{min}/{max}
     * @param min la population minimale.
     * @param max la population maximale.
     * @return une liste des villes trouvées, triées par population descendante.
     */
    @GetMapping("/search/population/between/{min}/{max}")
    public ResponseEntity<List<VilleDto>> getVillesByPopulationBetween(@PathVariable int min, @PathVariable int max) {
        List<Ville> villes = villeService.extractVillesByPopulationBetween(min, max);
        List<VilleDto> dtoList = villes.stream()
                .map(VilleMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    /**
     * Récupère toutes les villes d'un département donné dont la population est supérieure à min.
     * Endpoint : GET /villes/search/by-departement/{nomDepartement}/population/greater-than/{min}
     * @param nomDepartement le nom du département.
     * @param min la population minimale.
     * @return une liste des villes trouvées.
     */
    @GetMapping("/search/by-departement/{nomDepartement}/population/greater-than/{min}")
    public ResponseEntity<List<VilleDto>> getVillesByDepartementAndPopulationGreaterThan(@PathVariable String nomDepartement, @PathVariable int min) {
        List<Ville> villes = villeService.extractVillesByDepartementAndPopulationGreaterThan(nomDepartement, min);
        List<VilleDto> dtoList = villes.stream()
                .map(VilleMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    /**
     * Récupère toutes les villes d'un département donné dont la population est comprise entre min et max.
     * Endpoint : GET /villes/search/by-departement/{nomDepartement}/population/between/{min}/{max}
     * @param nomDepartement le nom du département.
     * @param min la population minimale.
     * @param max la population maximale.
     * @return une liste des villes trouvées.
     */
    @GetMapping("/search/by-departement/{nomDepartement}/population/between/{min}/{max}")
    public ResponseEntity<List<VilleDto>> getVillesByDepartementAndPopulationBetween(@PathVariable String nomDepartement, @PathVariable int min, @PathVariable int max) {
        List<Ville> villes = villeService.extractVillesByDepartementAndPopulationBetween(nomDepartement, min, max);
        List<VilleDto> dtoList = villes.stream()
                .map(VilleMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }
}
