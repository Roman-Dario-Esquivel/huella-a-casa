package ar.com.huella.huella.service.impl;

import ar.com.huella.huella.entity.CaseType;
import ar.com.huella.huella.entity.Found;
import ar.com.huella.huella.entity.Lost;
import ar.com.huella.huella.entity.Resolved;
import ar.com.huella.huella.exception.ResourceNotFoundException;
import ar.com.huella.huella.repository.IFoundRepository;
import ar.com.huella.huella.repository.ILostRepository;
import ar.com.huella.huella.repository.IResolvedRepository;
import ar.com.huella.huella.service.ICaseMigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaseMigrationService implements ICaseMigrationService {

    @Autowired
    private ILostRepository lostRepository;

    @Autowired
    private IFoundRepository foundRepository;

    @Autowired
    private IResolvedRepository resolvedRepository;

    @Override
    public Resolved migrateLostToResolved(Long lostId) {
        Lost lost = lostRepository.findById(lostId)
                .orElseThrow(() -> new ResourceNotFoundException("Lost not found with id " + lostId));

        // Crear Resolved a partir de Lost
        Resolved resolved = new Resolved();
        resolved.setSpecies(lost.getSpecies());
        resolved.setDescription(lost.getDescription());
        resolved.setDate(lost.getDate());
        resolved.setApproximateZone(lost.getApproximateZone());
        resolved.setPhoto(lost.getPhoto());
        resolved.setType(CaseType.LOST);  // opcional, para saber de qué tipo venía
        resolved.setRetained(false);

        // Guardar en Resolved
        Resolved savedResolved = resolvedRepository.save(resolved);

        // Eliminar de Lost
        lostRepository.delete(lost);

        return savedResolved;
    }

    @Override
    public Resolved migrateFoundToResolved(Long foundId) {
        Found found = foundRepository.findById(foundId)
                .orElseThrow(() -> new ResourceNotFoundException("Found not found with id " + foundId));

        // Crear Resolved a partir de Found
        Resolved resolved = new Resolved();
        resolved.setSpecies(found.getSpecies());
        resolved.setDescription(found.getDescription());
        resolved.setDate(found.getDate());
        resolved.setApproximateZone(found.getApproximateZone());
        resolved.setPhoto(found.getPhoto());
        resolved.setType(CaseType.FOUND); // opcional
        resolved.setRetained(found.isRetained());

        // Guardar en Resolved
        Resolved savedResolved = resolvedRepository.save(resolved);

        // Eliminar de Found
        foundRepository.delete(found);

        return savedResolved;
    }
}
