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
public class LoginRequest {
    private String email;
    private String password;
}
