package ar.com.huella.huella.securizer.repository;

import ar.com.huella.huella.securizer.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByName(String name);

    @Query(value = """
  SELECT * FROM users u
  WHERE (
    6371 * 2 * ASIN(
      SQRT(
        POWER(SIN(RADIANS(u.latitude - :lat) / 2), 2) +
        COS(RADIANS(:lat)) * COS(RADIANS(u.latitude)) *
        POWER(SIN(RADIANS(u.longitude - :lng) / 2), 2)
      )
    )
  ) <= u.radius_km
  """, nativeQuery = true)
    List<User> findUsersWithinRadius(
            @Param("lat") double lat,
            @Param("lng") double lng
    );

}
