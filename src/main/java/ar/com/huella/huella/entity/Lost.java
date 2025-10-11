package ar.com.huella.huella.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lost extends BaseCase {

    private String name;           // nombre de la mascota
    private String sex;            // sexo, porque lo sabe el dueño
    private String contactNumber;  // contacto
}
