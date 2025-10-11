package ar.com.huella.huella.repository;

import ar.com.huella.huella.entity.Lost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILostRepository extends JpaRepository<Lost, Long>{
    
}
