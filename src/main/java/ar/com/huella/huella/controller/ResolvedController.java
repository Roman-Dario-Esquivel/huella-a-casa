package ar.com.huella.huella.controller;

import ar.com.huella.huella.dto.ResolvedDto;
import ar.com.huella.huella.entity.Resolved;
import ar.com.huella.huella.exception.ResourceNotFoundException;
import ar.com.huella.huella.service.IResolvedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resolved")
@Tag(name = "Resuelto", description = "Operaciones basicas para resuelto como crear, actualizar, eliminar , devuelve uno y paginacion.")
public class ResolvedController {

    @Autowired
    private IResolvedService resolvedService;

    @GetMapping("/pages")
    @Operation(summary = "Metodo que devuelve una pagina.", description = "Metodo que te devuelve una pagina de Resuelto.")
    public Page<ResolvedDto> getResolved(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ResolvedDto> resolvedPage = resolvedService.getAllResolvedPaged(page, size);
        if (resolvedPage.isEmpty()) {
            throw new ResourceNotFoundException("No lost cases found");
        }
        return resolvedPage;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Metodo que devuelve uno.", description = "Metodo que te devuelve un Resuelto.")
    public Resolved getResolved(@PathVariable Long id) {
        return resolvedService.getResolvedById(id)
                .orElseThrow(() -> new RuntimeException("Resolved not found with id " + id));
    }

    @PostMapping("/create")
    @Operation(summary = "Metodo para crear.", description = "Metodo que sirve para crear un Resuelto.")
    public Resolved createResolved(@RequestBody Resolved resolved) {
        return resolvedService.createResolved(resolved);
    }

    @PutMapping("/upgrade/{id}")
    @Operation(summary = "Metodo para actualizar.", description = "Metodo que sirve para actualiza un Resuelto.")
    public Resolved updateResolved(@PathVariable Long id, @RequestBody Resolved resolved) {
        return resolvedService.updateResolved(id, resolved);
    }

    @DeleteMapping("/delede/{id}")
    @Operation(summary = "Metodo para eliminar.", description = "Metodo que sirve para eliminar un Resuelto.")
    public void deleteResolved(@PathVariable Long id) {
        resolvedService.deleteResolved(id);
    }
}
