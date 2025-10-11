package ar.com.huella.huella.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Found extends BaseCase {

    private String contactNumber;     // contacto
    private boolean retained;

}
