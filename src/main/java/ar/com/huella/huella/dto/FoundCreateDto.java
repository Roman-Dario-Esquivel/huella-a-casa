package ar.com.huella.huella.dto;

import ar.com.huella.huella.entity.CaseType;
import ar.com.huella.huella.entity.Location;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Validated
public class FoundCreateDto {
    
    private String photo;
    private String species;
    private String description;
    private LocalDate date;
    private String address;
    private Location approximateZone;
    private String contactNumber;
    private boolean retained;

}
