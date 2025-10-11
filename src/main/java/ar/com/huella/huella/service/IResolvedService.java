package ar.com.huella.huella.service;

import ar.com.huella.huella.dto.ResolvedDto;
import ar.com.huella.huella.entity.Resolved;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface IResolvedService {

   public Page<ResolvedDto> getAllResolvedPaged(int page, int size);

    Resolved createResolved(Resolved resolved);

    Optional<Resolved> getResolvedById(Long id);

    Resolved updateResolved(Long id, Resolved resolved);

    void deleteResolved(Long id);
}
