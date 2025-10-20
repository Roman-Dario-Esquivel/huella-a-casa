package ar.com.huella.huella.securizer.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Validated
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private double latitude;
    private double longitude;
    private double radiusKm;
    
}
