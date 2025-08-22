package fr.diginamic.hello.mappers;

import fr.diginamic.hello.Departement;
import fr.diginamic.hello.dto.DepartementDto;
import java.util.stream.Collectors;

public class DepartementMapper {


    /** SONARQUBE Said :
     * Add a private constructor to hide the implicit public one.
     *
     * prevent instantiation
     *
     * Reason: Your DepartementMapper is a utility class (all methods are static).
     * SonarQube warns because it doesn’t make sense to create an instance of it. By default,
     * Java gives it a public no-args constructor — Sonar doesn’t like that.
     */

    private DepartementMapper() {
        throw new UnsupportedOperationException("Utility class");




    }
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