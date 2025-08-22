package fr.diginamic.hello.mappers;

import fr.diginamic.hello.Departement;
import fr.diginamic.hello.dto.DepartementDto;
import java.util.stream.Collectors;

public class DepartementMapper {

    /**
     * Converts a Departement entity to a DepartementDto.
     * @param entity the Departement entity to convert.
     * @return the corresponding DepartementDto.
     */
    public static DepartementDto toDto(Departement entity) {
        if (entity == null) {
            return null;
        }
        DepartementDto dto = new DepartementDto();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setVilles(entity.getVilles()
                .stream()
                .map(VilleMapper::toDto)
                .toList());
        return dto;
    }
}