package ar.com.huella.huella.service;

import ar.com.huella.huella.dto.LostDto;
import ar.com.huella.huella.entity.Lost;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ILostService {

    public Page<LostDto> getAllLostPaged(int page, int size);

    Lost createLost(Lost lost);

    Optional<Lost> getLostById(Long id);

    Lost updateLost(Long id, Lost lost);

    void deleteLost(Long id);
}
