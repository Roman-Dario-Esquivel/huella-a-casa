package ar.com.huella.huella.service;

import ar.com.huella.huella.dto.FoundCreateDto;
import ar.com.huella.huella.dto.FoundDto;
import ar.com.huella.huella.entity.Found;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface IFoundService {

    public Page<FoundDto> getAllFoundPaged(int page, int size);

    Found createFound(FoundCreateDto found);

    Optional<Found> getFoundById(Long id);

    Found updateFound(Long id, Found found);

    void deleteFound(Long id);
}
