package ar.com.huella.huella.securizer.service;

import ar.com.huella.huella.securizer.entity.Role;
import ar.com.huella.huella.securizer.entity.RoleEnum;
import java.util.List;
import java.util.Optional;

public interface IRoleService {

    public Role saveRole(Role role);

    public Optional<Role> getByRolNombre(RoleEnum rolNombre);

    public List<Role> getAllRoles();

    public void deleteUser(Long id);
}
