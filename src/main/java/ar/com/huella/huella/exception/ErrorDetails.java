package ar.com.huella.huella.exception;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDetails {
     private LocalDateTime timestamp;
    private String message;
    private String details;

    
}
