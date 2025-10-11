package ar.com.huella.huella.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String photo;
    private String species;
    private String description;
    private LocalDate date;
    private String address;
    @Embedded
    private Location approximateZone;
    // Tipo de caso: PERDIDO o ENCONTRADO
    @Enumerated(EnumType.STRING)
    private CaseType type;

}
