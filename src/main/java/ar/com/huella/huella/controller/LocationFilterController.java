package ar.com.huella.huella.controller;

import ar.com.huella.huella.entity.Found;
import ar.com.huella.huella.entity.Lost;
import ar.com.huella.huella.service.impl.LocationFilterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/filter")
@Tag(name = "Filtrado", description = "Operaciones basicas para Location Filter en gral o encontrado y perdido.")
public class LocationFilterController {

    @Autowired
    private LocationFilterService locationFilterService;

    @GetMapping("/nearby")
    @Operation(summary = "Metodo que devuelve todos en un radio.", description = "Metodo que te devuelve todos los animales en un radio")
    public ResponseEntity<Map<String, Object>> getNearbyAnimals(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5") double radiusKm) {

        Map<String, Object> result = locationFilterService.getAllWithinRadius(latitude, longitude, radiusKm);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/Lost/nearby")
    @Operation(summary = "Metodo que devuelve en un radio los  perdidos.", description = "Metodo que te devuelve todo los perdidos en un radio.")
    public ResponseEntity<List<Lost>> getNearbyLost(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5") double radiusKm) {

        List<Lost> results = locationFilterService.getLostWithinRadius(latitude, longitude, radiusKm);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/Found/nearby")
    @Operation(summary = "Metodo que devuelve en un radio los encontrados.", description = "Metodo que te devuelve todo los encontrados en un radio.")
    public ResponseEntity<List<Found>> getNearbyFound(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5") double radiusKm) {

        List<Found> results = locationFilterService.getFoundWithinRadius(latitude, longitude, radiusKm);
        return ResponseEntity.ok(results);
    }

}
