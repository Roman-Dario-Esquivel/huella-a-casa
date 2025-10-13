package ar.com.huella.huella.securizer.service.implementation;

import ar.com.huella.huella.securizer.entity.Role;
import ar.com.huella.huella.securizer.entity.RoleEnum;
import ar.com.huella.huella.securizer.repository.IRoleRepository;
import ar.com.huella.huella.securizer.service.IRoleService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService{

    @Autowired
    private IRoleRepository roleRepository;
    
    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> getByRolNombre(RoleEnum rolNombre){
        return roleRepository.findByRoleEnum(rolNombre);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        roleRepository.deleteById(id);
    }
}
