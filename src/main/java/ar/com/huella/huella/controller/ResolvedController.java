package ar.com.huella.huella.controller;

import ar.com.huella.huella.dto.ResolvedDto;
import ar.com.huella.huella.entity.Resolved;
import ar.com.huella.huella.exception.ResourceNotFoundException;
import ar.com.huella.huella.service.IResolvedService;
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
public class ResolvedController {
    
    @Autowired
    private IResolvedService resolvedService;

    @GetMapping
    public Page<ResolvedDto> getResolved(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
         Page<ResolvedDto> resolvedPage = resolvedService.getAllResolvedPaged(page, size);
        if (resolvedPage.isEmpty()) {
            throw new ResourceNotFoundException("No lost cases found");
        }
        return resolvedPage;
    }
    

    @GetMapping("/{id}")
    public Resolved getResolved(@PathVariable Long id) {
        return resolvedService.getResolvedById(id)
                              .orElseThrow(() -> new RuntimeException("Resolved not found with id " + id));
    }

    @PostMapping
    public Resolved createResolved(@RequestBody Resolved resolved) {
        return resolvedService.createResolved(resolved);
    }

    @PutMapping("/{id}")
    public Resolved updateResolved(@PathVariable Long id, @RequestBody Resolved resolved) {
        return resolvedService.updateResolved(id, resolved);
    }

    @DeleteMapping("/{id}")
    public void deleteResolved(@PathVariable Long id) {
        resolvedService.deleteResolved(id);
    }
}
