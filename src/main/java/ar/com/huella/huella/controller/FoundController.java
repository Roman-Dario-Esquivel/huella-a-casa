package ar.com.huella.huella.controller;

import ar.com.huella.huella.dto.FoundCreateDto;
import ar.com.huella.huella.dto.FoundDto;
import ar.com.huella.huella.entity.Found;
import ar.com.huella.huella.exception.ResourceNotFoundException;
import ar.com.huella.huella.service.IFoundService;
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
@RequestMapping("/api/found")
@Tag(name = "Encontrado", description = "Operaciones basicas para encontrado como crear, actualizar, eliminar , devuelve uno y paginacion.")
public class FoundController {

    @Autowired
    private IFoundService foundService;

    @GetMapping("/pages")
    @Operation(summary = "Metodo que devuelve una pagina.", description = "Metodo que te devuelve una pagina de encontrados.")
    public Page<FoundDto> getFounds(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<FoundDto> foundPage = foundService.getAllFoundPaged(page, size);
        if (foundPage.isEmpty()) {
            throw new ResourceNotFoundException("No lost cases found");
        }
        return foundPage;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Metodo que devuelve uno.", description = "Metodo que te devuelve un encontrado.")
    public Found getFound(@PathVariable Long id) {
        return foundService.getFoundById(id)
                .orElseThrow(() -> new RuntimeException("Found not found with id " + id));
    }

    @PostMapping("/create")
    @Operation(summary = "Metodo para crear.", description = "Metodo que sirve para crear un encontrado.")
    public Found createFound(@RequestBody FoundCreateDto found) {
        return foundService.createFound(found);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Metodo para actualizar.", description = "Metodo que sirve para actualiza un encontrado.")
    public Found updateFound(@PathVariable Long id, @RequestBody Found found) {
        return foundService.updateFound(id, found);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Metodo para eliminar.", description = "Metodo que sirve para eliminar un encontrado.")
    public void deleteFound(@PathVariable Long id) {
        foundService.deleteFound(id);
    }
}
