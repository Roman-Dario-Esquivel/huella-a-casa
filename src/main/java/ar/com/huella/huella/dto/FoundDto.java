package ar.com.huella.huella.dto;

import org.springframework.validation.annotation.Validated;
import ar.com.huella.huella.entity.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Validated
public class FoundDto {

    private Long id;
    private String photo;
    private String species;
    private String description;
    private LocalDate date;
    private String address;
    private Location approximateZone;
    private CaseType type;

    private String contactNumber;
    private boolean retained;

    private String filter;
}
