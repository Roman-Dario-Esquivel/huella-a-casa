package ar.com.huella.huella.dto;

import ar.com.huella.huella.entity.*;
import lombok.*;
import java.time.LocalDate;
import org.springframework.validation.annotation.Validated;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Validated
public class ResolvedDto {
     private Long id;
    private String photo;
    private String species;
    private String description;
    private LocalDate date;
    private String address;
    private Location approximateZone;
    private CaseType type;

    private boolean retained;

    private String filter; 
}
