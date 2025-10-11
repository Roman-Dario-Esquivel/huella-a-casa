
package ar.com.huella.huella.securizer.repository;

import ar.com.huella.huella.securizer.entity.Role;
import ar.com.huella.huella.securizer.entity.RoleEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByRoleEnum(RoleEnum roleEnum);
}
