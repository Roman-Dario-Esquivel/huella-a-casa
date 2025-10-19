package ar.com.huella.huella.repository;

import ar.com.huella.huella.entity.Lost;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ILostRepository extends JpaRepository<Lost, Long> {

    @Query(value = """
    SELECT * FROM lost l
    WHERE (
        6371 * 2 * ASIN(
            SQRT(
                POWER(SIN(RADIANS(l.latitude - :latitude) / 2), 2) +
                COS(RADIANS(:latitude)) * COS(RADIANS(l.latitude)) *
                POWER(SIN(RADIANS(l.longitude - :longitude) / 2), 2)
            )
        )
    ) <= :radius
    """, nativeQuery = true)
    List<Lost> findAllWithinRadius(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("radius") double radius
    );
}
