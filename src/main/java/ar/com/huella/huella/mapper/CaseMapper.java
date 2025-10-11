package ar.com.huella.huella.mapper;

import ar.com.huella.huella.dto.*;
import ar.com.huella.huella.entity.*;

public class CaseMapper {
    // LOST
    public static LostDto toDTO(Lost lost) {
        return LostDto.builder()
                .id(lost.getId())
                .photo(lost.getPhoto())
                .species(lost.getSpecies())
                .description(lost.getDescription())
                .date(lost.getDate())
                .address(lost.getAddress())
                .approximateZone(lost.getApproximateZone())
                .type(lost.getType())
                .name(lost.getName())
                .sex(lost.getSex())
                .contactNumber(lost.getContactNumber())
                .filter(buildFilter(
                        lost.getPhoto(),
                        lost.getSpecies(),
                        lost.getDescription(),
                        lost.getDate() != null ? lost.getDate().toString() : "",
                        lost.getAddress(),
                        lost.getName(),
                        lost.getSex(),
                        lost.getContactNumber()
                ))
                .build();
    }

    // FOUND
    public static FoundDto toDTO(Found found) {
        return FoundDto.builder()
                .id(found.getId())
                .photo(found.getPhoto())
                .species(found.getSpecies())
                .description(found.getDescription())
                .date(found.getDate())
                .address(found.getAddress())
                .approximateZone(found.getApproximateZone())
                .type(found.getType())
                .contactNumber(found.getContactNumber())
                .retained(found.isRetained())
                .filter(buildFilter(
                        found.getPhoto(),
                        found.getSpecies(),
                        found.getDescription(),
                        found.getDate() != null ? found.getDate().toString() : "",
                        found.getAddress(),
                        found.getContactNumber(),
                        String.valueOf(found.isRetained())
                ))
                .build();
    }

    // RESOLVED
    public static ResolvedDto toDTO(Resolved resolved) {
        return ResolvedDto.builder()
                .id(resolved.getId())
                .photo(resolved.getPhoto())
                .species(resolved.getSpecies())
                .description(resolved.getDescription())
                .date(resolved.getDate())
                .address(resolved.getAddress())
                .approximateZone(resolved.getApproximateZone())
                .type(resolved.getType())
                .retained(resolved.isRetained())
                .filter(buildFilter(
                        resolved.getPhoto(),
                        resolved.getSpecies(),
                        resolved.getDescription(),
                        resolved.getDate() != null ? resolved.getDate().toString() : "",
                        resolved.getAddress(),
                        String.valueOf(resolved.isRetained())
                ))
                .build();
    }

    // Utilidad común
    private static String buildFilter(String... values) {
        StringBuilder sb = new StringBuilder();
        for (String v : values) {
            if (v != null && !v.isBlank()) {
                sb.append(v).append(" ");
            }
        }
        return sb.toString().trim();
    }
}
