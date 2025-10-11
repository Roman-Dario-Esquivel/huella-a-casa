package ar.com.huella.huella.controller;

import ar.com.huella.huella.entity.Resolved;
import ar.com.huella.huella.service.ICaseMigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/migrate")
public class CaseMigrationController {
    
    @Autowired
    private ICaseMigrationService migrationService;

    @PostMapping("/lost/{id}")
    public Resolved migrateLostToResolved(@PathVariable Long id) {
        return migrationService.migrateLostToResolved(id);
    }

    @PostMapping("/found/{id}")
    public Resolved migrateFoundToResolved(@PathVariable Long id) {
        return migrationService.migrateFoundToResolved(id);
    }
}
