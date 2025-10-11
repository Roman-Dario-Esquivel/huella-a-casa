package ar.com.huella.huella.service.impl;

import ar.com.huella.huella.dto.ResolvedDto;
import ar.com.huella.huella.entity.Resolved;
import ar.com.huella.huella.exception.ResourceNotFoundException;
import ar.com.huella.huella.mapper.CaseMapper;
import ar.com.huella.huella.repository.IResolvedRepository;
import ar.com.huella.huella.service.IResolvedService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ResolvedService implements IResolvedService {

    @Autowired
    private IResolvedRepository resolvedRepository;

    @Override
    public Page<ResolvedDto> getAllResolvedPaged(int page, int size) {
        return resolvedRepository.findAll(PageRequest.of(page, size))
                .map(CaseMapper::toDTO);
    }

    @Override
    public Resolved createResolved(Resolved resolved) {
        return resolvedRepository.save(resolved);
    }

    @Override
    public Optional<Resolved> getResolvedById(Long id) {
        return resolvedRepository.findById(id);
    }

    @Override
    public Resolved updateResolved(Long id, Resolved updatedResolved) {
        return resolvedRepository.findById(id).map(existing -> {
            existing.setSpecies(updatedResolved.getSpecies());
            existing.setDescription(updatedResolved.getDescription());
            existing.setDate(updatedResolved.getDate());
            existing.setApproximateZone(updatedResolved.getApproximateZone());
            existing.setRetained(updatedResolved.isRetained());
            return resolvedRepository.save(existing);
        }).orElseThrow(() -> new ResourceNotFoundException("Resolved not found with id " + id));
    }

    @Override
    public void deleteResolved(Long id) {
        resolvedRepository.deleteById(id);
    }
}
