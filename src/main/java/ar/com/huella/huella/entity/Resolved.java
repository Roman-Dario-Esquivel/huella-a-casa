package ar.com.huella.huella.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resolved  extends BaseCase {

    private boolean retained;   

}
