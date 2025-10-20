package ar.com.huella.huella.service.impl;

import ar.com.huella.huella.dto.LostCreateDto;
import ar.com.huella.huella.dto.LostDto;
import ar.com.huella.huella.entity.CaseType;
import ar.com.huella.huella.entity.Lost;
import ar.com.huella.huella.exception.ResourceNotFoundException;
import ar.com.huella.huella.mapper.CaseMapper;
import ar.com.huella.huella.repository.ILostRepository;
import ar.com.huella.huella.securizer.entity.User;
import ar.com.huella.huella.securizer.repository.IUserRepository;
import ar.com.huella.huella.securizer.service.implementation.EmailService;
import ar.com.huella.huella.service.ILostService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class LostService implements ILostService {

    @Autowired
    private ILostRepository lostRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private EmailService emailService;

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
        Lost saved = lostRepository.save(lost);

        double lat = saved.getApproximateZone().getLatitude();
        double lng = saved.getApproximateZone().getLongitude();
        List<User> nearbyUsers = userRepository.findUsersWithinRadius(lat, lng);

        notificarUsuariosCercanos(saved, "lost");
        
        return saved;
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

    private void notificarUsuariosCercanos(Lost saved, String type) {
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
            vars.put("subject", (type.equals("lost") ?
                    "🐾 Mascota perdida cerca de ti" :
                    "🎉 Mascota encontrada cerca de ti"));

            emailService.sendCaseNotification(
                    user.getEmail(),
                    (String) vars.get("subject"),
                    "case-notification",
                    vars
            );
        }
    }

    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }


}
