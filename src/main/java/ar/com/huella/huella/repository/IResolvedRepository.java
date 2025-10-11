package ar.com.huella.huella.repository;

import ar.com.huella.huella.entity.Resolved;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IResolvedRepository extends JpaRepository<Resolved, Long>{
    
}
