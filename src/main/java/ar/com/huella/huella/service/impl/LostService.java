package ar.com.huella.huella.service.impl;

import ar.com.huella.huella.dto.LostCreateDto;
import ar.com.huella.huella.dto.LostDto;
import ar.com.huella.huella.entity.CaseType;
import ar.com.huella.huella.entity.Lost;
import ar.com.huella.huella.exception.ResourceNotFoundException;
import ar.com.huella.huella.mapper.CaseMapper;
import ar.com.huella.huella.repository.ILostRepository;
import ar.com.huella.huella.service.ILostService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class LostService implements ILostService {

    @Autowired
    private ILostRepository lostRepository;

    @Override
    public Page<LostDto> getAllLostPaged(int page, int size) {
        Page<Lost> lostPage = lostRepository.findAll(PageRequest.of(page, size));
        return lostPage.map(CaseMapper::toDTO);
    }

    @Override
    public Lost createLost(LostCreateDto lostDto) {

        Lost lost = new Lost();
        lost.setPhoto(lostDto.getPhoto());
        lost.setSpecies(lostDto.getSpecies());
        lost.setDescription(lostDto.getDescription());
        lost.setDate(lostDto.getDate());
        lost.setAddress(lostDto.getAddress());
        lost.setApproximateZone(lostDto.getApproximateZone());
        lost.setType(CaseType.LOST);
        lost.setName(lostDto.getName());
        lost.setSex(lostDto.getSex());
        lost.setContactNumber(lostDto.getContactNumber());

        return lostRepository.save(lost);
    }

    @Override
    public Optional<Lost> getLostById(Long id) {
        return lostRepository.findById(id);
    }

    @Override
    public Lost updateLost(Long id, Lost updatedLost) {
        return lostRepository.findById(id).map(existing -> {
            existing.setName(updatedLost.getName());
            existing.setSpecies(updatedLost.getSpecies());
            existing.setSex(updatedLost.getSex());
            existing.setDescription(updatedLost.getDescription());
            existing.setDate(updatedLost.getDate());
            existing.setApproximateZone(updatedLost.getApproximateZone());
            existing.setContactNumber(updatedLost.getContactNumber());
            return lostRepository.save(existing);
        }).orElseThrow(() -> new ResourceNotFoundException("Lost not found with id " + id));
    }

    @Override
    public void deleteLost(Long id) {
        lostRepository.deleteById(id);
    }

}
