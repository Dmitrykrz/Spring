package fr.diginamic.hello.services;

import fr.diginamic.hello.Departement;
import fr.diginamic.hello.repositories.DepartementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartementService {

    private final DepartementRepository departementRepository;

    public DepartementService(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    /** Extrait tous les départements */
    public List<Departement> extractDepartements() {
        return departementRepository.findAll();
    }

    /** Extrait un département par son ID */
    public Optional<Departement> extractDepartement(int id) {
        return departementRepository.findById(id);
    }

    /** Extrait un département par son nom */
    public Optional<Departement> extractDepartement(String nom) {
        return departementRepository.findByNomIgnoreCase(nom);
    }

    /** Extrait un département par son code */
    public Optional<Departement> extractDepartementByCode(String code) {
        return departementRepository.findByCodeIgnoreCase(code);
    }

    /** Insère un département */
    public void insertDepartement(Departement d) {
        departementRepository.save(d);
    }

    /** Modifie un département existant */
    public void modifierDepartement(int id, Departement departementModifie) {
        departementRepository.findById(id).ifPresent(existing -> {
            existing.setNom(departementModifie.getNom());
            existing.setCode(departementModifie.getCode());
            existing.setVilles(departementModifie.getVilles());
            departementRepository.save(existing);
        });
    }

    /** Supprime un département par son ID */
    public void supprimerDepartement(int id) {
        departementRepository.deleteById(id);
    }
}