package ar.com.huella.huella.controller;

import ar.com.huella.huella.dto.FoundDto;
import ar.com.huella.huella.entity.Found;
import ar.com.huella.huella.exception.ResourceNotFoundException;
import ar.com.huella.huella.service.IFoundService;
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
public class FoundController {

    @Autowired
    private IFoundService foundService;

    @GetMapping
    public Page<FoundDto> getFounds(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
         Page<FoundDto> foundPage = foundService.getAllFoundPaged(page, size);
        if (foundPage.isEmpty()) {
            throw new ResourceNotFoundException("No lost cases found");
        }
        return foundPage;
    }

    @GetMapping("/{id}")
    public Found getFound(@PathVariable Long id) {
        return foundService.getFoundById(id)
                .orElseThrow(() -> new RuntimeException("Found not found with id " + id));
    }

    @PostMapping
    public Found createFound(@RequestBody Found found) {
        return foundService.createFound(found);
    }

    @PutMapping("/{id}")
    public Found updateFound(@PathVariable Long id, @RequestBody Found found) {
        return foundService.updateFound(id, found);
    }

    @DeleteMapping("/{id}")
    public void deleteFound(@PathVariable Long id) {
        foundService.deleteFound(id);
    }
}
