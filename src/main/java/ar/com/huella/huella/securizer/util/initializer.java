package ar.com.huella.huella.securizer.util;

import ar.com.huella.huella.securizer.entity.Role;
import ar.com.huella.huella.securizer.entity.RoleEnum;
import ar.com.huella.huella.securizer.entity.User;
import ar.com.huella.huella.securizer.repository.IRoleRepository;
import ar.com.huella.huella.securizer.repository.IUserRepository;
import ar.com.huella.huella.securizer.service.IRoleService;
import ar.com.huella.huella.securizer.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class initializer implements CommandLineRunner {

    @Autowired
    IRoleService rolService;

    @Autowired
    IUserService userService;

    @Autowired
    IUserRepository userRepository;
    
    @Autowired
    IRoleRepository roleRepository;

    @Override

    public void run(String... args) throws Exception {
        if (rolService.getByRolNombre(RoleEnum.ROLE_ADMIN).isEmpty()) {
            rolService.saveRole(new Role(RoleEnum.ROLE_ADMIN));
            System.out.println("Se genero el rol admin.");
        }

        if (rolService.getByRolNombre(RoleEnum.ROLE_USER).isEmpty()) {
            rolService.saveRole(new Role(RoleEnum.ROLE_USER));
            System.out.println("Se genero el rol usuario.");
        }
        String User1 = "administrador";

        if (!userRepository.existsByName(User1)) {

            String mail = "asistenciainformaticarde@gmai.com";

            User user = new User();
            user.setName(User1);
            user.setEmail(mail);
            String password = "prueba1234";
            user.setPassword(password);
            Role use = roleRepository.findByRoleEnum(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("El rol ROLE_USER no existe"));
            Role admin = roleRepository.findByRoleEnum(RoleEnum.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("El rol ROLE_ADMIN no existe"));
            user.getRoles().add(admin);
            user.getRoles().add(use);
            userService.saveUser(user);
            System.out.println("Se genero el usuario.");
        }
    }
}
