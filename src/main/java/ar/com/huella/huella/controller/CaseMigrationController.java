package ar.com.huella.huella.controller;

import ar.com.huella.huella.entity.Resolved;
import ar.com.huella.huella.service.ICaseMigrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/migrate")
@Tag(name = "Migracion", description = "Operaciones de mover de perdido o encontrado resuelto")
public class CaseMigrationController {
    
    @Autowired
    private ICaseMigrationService migrationService;

    @PostMapping("/lost/{id}")
    @Operation(summary = "Metodo que mueve de perdido a resuelto ", description = "Metodo que mueve de base de datos de perdido a  resuelto")
    public Resolved migrateLostToResolved(@PathVariable Long id) {
        return migrationService.migrateLostToResolved(id);
    }

    @PostMapping("/found/{id}")
    @Operation(summary = "Metodo que mueve de encontrado a resuelto ", description = "Metodo que mueve de base de datos de encontrado a  resuelto")
    public Resolved migrateFoundToResolved(@PathVariable Long id) {
        return migrationService.migrateFoundToResolved(id);
    }
}
