package ar.com.huella.huella.service.impl;

import ar.com.huella.huella.dto.FoundCreateDto;
import ar.com.huella.huella.dto.FoundDto;
import ar.com.huella.huella.entity.CaseType;
import ar.com.huella.huella.entity.Found;
import ar.com.huella.huella.exception.ResourceNotFoundException;
import ar.com.huella.huella.mapper.CaseMapper;
import ar.com.huella.huella.repository.IFoundRepository;
import ar.com.huella.huella.securizer.entity.User;
import ar.com.huella.huella.securizer.repository.IUserRepository;
import ar.com.huella.huella.securizer.service.implementation.EmailService;
import ar.com.huella.huella.service.IFoundService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Service
public class FoundService implements IFoundService {

    @Autowired
    private IFoundRepository foundRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private EmailService emailService;
    
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
        Found saved = foundRepository.save(found);

        double lat = saved.getApproximateZone().getLatitude();
        double lng = saved.getApproximateZone().getLongitude();
        List<User> nearbyUsers = userRepository.findUsersWithinRadius(lat, lng);
        
        notificarUsuariosCercanos(saved, "found");
        
        return saved;
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

    private void notificarUsuariosCercanos(Found saved, String type) {
        double lat = saved.getApproximateZone().getLatitude();
        double lng = saved.getApproximateZone().getLongitude();

        List<User> nearbyUsers = userRepository.findUsersWithinRadius(lat, lng);

        for (User user : nearbyUsers) {
            double distance = calcularDistancia(lat, lng, user.getLatitude(), user.getLongitude());

            Map<String, Object> vars = new HashMap<>();
            vars.put("type", type);
            vars.put("userName", user.getName());
            vars.put("description", saved.getDescription());
            vars.put("address", saved.getAddress());
            vars.put("distance", String.format("%.1f", distance));
            vars.put("caseUrl", "http://tusitio.com/api/cases/" + type + "/" + saved.getId());
            vars.put("year", LocalDate.now().getYear());
            vars.put("subject", "🎉 Mascota encontrada cerca de ti");

            emailService.sendCaseNotification(
                    user.getEmail(),
                    (String) vars.get("subject"),
                    "case-notification",
                    vars
            );
        }
    }

    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
    
}
