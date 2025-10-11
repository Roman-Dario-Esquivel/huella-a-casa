package ar.com.huella.huella.controller;

import ar.com.huella.huella.dto.LostDto;
import ar.com.huella.huella.entity.Lost;
import ar.com.huella.huella.exception.ResourceNotFoundException;
import ar.com.huella.huella.service.ILostService;
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
public class LostController {

    @Autowired
    private ILostService lostService;

    @GetMapping
    public Page<LostDto> getLosts(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
         Page<LostDto> lostPage = lostService.getAllLostPaged(page, size);
        if (lostPage.isEmpty()) {
            throw new ResourceNotFoundException("No lost cases found");
        }
        return lostPage;
    }

    @GetMapping("/{id}")
    public Lost getLost(@PathVariable Long id) {
        return lostService.getLostById(id)
                .orElseThrow(() -> new RuntimeException("Lost not found with id " + id));
    }

    @PostMapping
    public Lost createLost(@RequestBody Lost lost) {
        return lostService.createLost(lost);
    }

    @PutMapping("/{id}")
    public Lost updateLost(@PathVariable Long id, @RequestBody Lost lost) {
        return lostService.updateLost(id, lost);
    }

    @DeleteMapping("/{id}")
    public void deleteLost(@PathVariable Long id) {
        lostService.deleteLost(id);
    }
}
