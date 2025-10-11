package IRoleRepository;

import ar.com.huella.huella.securizer.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface java extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
