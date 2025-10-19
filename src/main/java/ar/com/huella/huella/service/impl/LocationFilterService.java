package ar.com.huella.huella.service.impl;

import ar.com.huella.huella.entity.Found;
import ar.com.huella.huella.entity.Lost;
import ar.com.huella.huella.repository.IFoundRepository;
import ar.com.huella.huella.repository.ILostRepository;
import ar.com.huella.huella.service.ILocationFilterService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationFilterService implements ILocationFilterService {

    private final ILostRepository lostRepository;
    private final IFoundRepository foundRepository;

    public Map<String, Object> getAllWithinRadius(double latitude, double longitude, double radiusKm) {
        Map<String, Object> response = new HashMap<>();

        List<Lost> lostList = lostRepository.findAllWithinRadius(latitude, longitude, radiusKm);
        List<Found> foundList = foundRepository.findAllWithinRadius(latitude, longitude, radiusKm);

        response.put("lost", lostList);
        response.put("found", foundList);
        response.put("countLost", lostList.size());
        response.put("countFound", foundList.size());
        response.put("radiusKm", radiusKm);

        return response;
    }

    public List<Lost> getLostWithinRadius(double latitude, double longitude, double radiusKm) {
        return lostRepository.findAllWithinRadius(latitude, longitude, radiusKm);
    }

    public List<Found> getFoundWithinRadius(double latitude, double longitude, double radiusKm) {
        return foundRepository.findAllWithinRadius(latitude, longitude, radiusKm);
    }

}
