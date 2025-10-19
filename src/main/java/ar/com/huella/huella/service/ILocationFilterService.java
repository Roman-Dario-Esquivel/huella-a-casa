package ar.com.huella.huella.service;

import ar.com.huella.huella.entity.Found;
import ar.com.huella.huella.entity.Lost;
import java.util.List;
import java.util.Map;

public interface ILocationFilterService {

    public Map<String, Object> getAllWithinRadius(double latitude, double longitude, double radiusKm);

    public List<Lost> getLostWithinRadius(double latitude, double longitude, double radiusKm);

    public List<Found> getFoundWithinRadius(double latitude, double longitude, double radiusKm);
}
