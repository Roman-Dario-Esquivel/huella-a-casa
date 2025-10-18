package ar.com.huella.huella.controller;

import ar.com.huella.huella.dto.LostCreateDto;
import ar.com.huella.huella.dto.LostDto;
import ar.com.huella.huella.entity.Lost;
import ar.com.huella.huella.exception.ResourceNotFoundException;
import ar.com.huella.huella.service.ILostService;
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
@RequestMapping("/api/lost")
@Tag(name = "Perdidos", description = "Operaciones basicas para Perdidos como crear, actualizar, eliminar , devuelve uno y paginacion.")
public class LostController {

    @Autowired
    private ILostService lostService;

    @GetMapping("/pages")
    @Operation(summary = "Metodo que devuelve una pagina.", description = "Metodo que te devuelve una pagina de Perdidos.")
    public Page<LostDto> getLosts(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<LostDto> lostPage = lostService.getAllLostPaged(page, size);
        if (lostPage.isEmpty()) {
            throw new ResourceNotFoundException("No lost cases found");
        }
        return lostPage;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Metodo que devuelve uno.", description = "Metodo que te devuelve un Perdido.")
    public Lost getLost(@PathVariable Long id) {
        return lostService.getLostById(id)
                .orElseThrow(() -> new RuntimeException("Lost not found with id " + id));
    }

    @PostMapping("/create")
    @Operation(summary = "Metodo para crear.", description = "Metodo que sirve para crear un Perdido.")
    public Lost createLost(@RequestBody LostCreateDto lost) {
        return lostService.createLost(lost);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Metodo para actualizar.", description = "Metodo que sirve para actualiza un Perdido.")
    public Lost updateLost(@PathVariable Long id, @RequestBody Lost lost) {
        return lostService.updateLost(id, lost);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Metodo para eliminar.", description = "Metodo que sirve para eliminar un Perdido.")
    public void deleteLost(@PathVariable Long id) {
        lostService.deleteLost(id);
    }
}
