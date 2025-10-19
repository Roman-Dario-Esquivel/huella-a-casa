package ar.com.huella.huella.entity;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {

    private Double latitude;
    private Double longitude;
    
}
