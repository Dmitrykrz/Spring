package fr.diginamic.hello.mappers;

import fr.diginamic.hello.Departement;
import fr.diginamic.hello.Ville;
import fr.diginamic.hello.dto.VilleDto;

/**
 * Classe utilitaire pour mapper les entités Ville et Departement vers et depuis les DTO.
 */
public class VilleMapper {

    /**
     * Convertit une entité Ville en un DTO VilleDto.
     * @param ville l'entité Ville à convertir.
     * @return le DTO correspondant.
     */
    public static VilleDto toDto(Ville ville) {
        VilleDto dto = new VilleDto();
        dto.setId(ville.getId());
        dto.setNom(ville.getNom());
        dto.setNbHabitants(ville.getNbHabitants());
        // Utilise l'ID du département au lieu du nom
        dto.setIdDepartement(ville.getDepartement().getId());
        return dto;
    }

    /**
     * Convertit un DTO VilleDto en une entité Ville.
     * @param dto le DTO à convertir.
     * @param departement l'entité Departement associée.
     * @return l'entité Ville correspondante.
     */
    public static Ville toEntity(VilleDto dto, Departement departement) {
        Ville ville = new Ville();
        ville.setNom(dto.getNom());
        ville.setNbHabitants(dto.getNbHabitants());
        ville.setDepartement(departement);
        return ville;
    }
}
