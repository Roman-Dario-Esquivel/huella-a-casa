package ar.com.huella.huella.service.impl;

import ar.com.huella.huella.dto.FoundCreateDto;
import ar.com.huella.huella.dto.FoundDto;
import ar.com.huella.huella.entity.CaseType;
import ar.com.huella.huella.entity.Found;
import ar.com.huella.huella.exception.ResourceNotFoundException;
import ar.com.huella.huella.mapper.CaseMapper;
import ar.com.huella.huella.repository.IFoundRepository;
import ar.com.huella.huella.service.IFoundService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FoundService implements IFoundService {

    @Autowired
    private IFoundRepository foundRepository;

    @Override
    public Page<FoundDto> getAllFoundPaged(int page, int size) {
        return foundRepository.findAll(PageRequest.of(page, size))
                              .map(CaseMapper::toDTO);
    }

    @Override
    public Found createFound(FoundCreateDto foundDto) {
        Found found = new Found();
    found.setPhoto(foundDto.getPhoto());
    found.setSpecies(foundDto.getSpecies());
    found.setDescription(foundDto.getDescription());
    found.setDate(foundDto.getDate());
    found.setAddress(foundDto.getAddress());
    found.setApproximateZone(foundDto.getApproximateZone());
    found.setType(CaseType.FOUND);
    found.setContactNumber(foundDto.getContactNumber());
    found.setRetained(foundDto.isRetained());
        return foundRepository.save(found);
    }

    @Override
    public Optional<Found> getFoundById(Long id) {
        return foundRepository.findById(id);
    }

    @Override
    public Found updateFound(Long id, Found updatedFound) {
        return foundRepository.findById(id).map(existing -> {
            existing.setSpecies(updatedFound.getSpecies());
            existing.setDescription(updatedFound.getDescription());
            existing.setDate(updatedFound.getDate());
            existing.setApproximateZone(updatedFound.getApproximateZone());
            existing.setContactNumber(updatedFound.getContactNumber());
            existing.setRetained(updatedFound.isRetained());
            return foundRepository.save(existing);
        }).orElseThrow(() -> new ResourceNotFoundException("Found not found with id " + id));
    }

    @Override
    public void deleteFound(Long id) {
        foundRepository.deleteById(id);
    }

}
