package ar.com.huella.huella.repository;

import ar.com.huella.huella.entity.Found;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFoundRepository extends JpaRepository<Found, Long> {
    
}
