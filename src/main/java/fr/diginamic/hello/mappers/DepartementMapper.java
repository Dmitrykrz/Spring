package fr.diginamic.hello.mappers;

import fr.diginamic.hello.Departement;
import fr.diginamic.hello.dto.DepartementDto;

public class DepartementMapper {
    private DepartementMapper() {
        throw new UnsupportedOperationException("Utility class");
    }
    /**
     * Convertit une entité Departement en DTO.
     *
     * @param departement l'entité Departement.
     * @return le DTO correspondant.
     */
    public static DepartementDto toDto(Departement departement) {
        if (departement == null) {
            return null;
        }
        DepartementDto dto = new DepartementDto();
        dto.setId(departement.getId());
        dto.setNom(departement.getNom());
        dto.setCode(departement.getCode());
        return dto;
    }

    /**
     * Convertit un DTO Departement en entité.
     *
     * @param dto le DTO.
     * @return l'entité Departement correspondante.
     */
    public static Departement toEntity(DepartementDto dto) {
        if (dto == null) {
            return null;
        }
        Departement departement = new Departement();
        departement.setId(dto.getId());
        departement.setNom(dto.getNom());
        departement.setCode(dto.getCode());
        return departement;
    }
}