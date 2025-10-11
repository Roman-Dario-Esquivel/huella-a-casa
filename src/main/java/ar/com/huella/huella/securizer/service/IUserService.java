package ar.com.huella.huella.securizer.service;

import ar.com.huella.huella.securizer.entity.User;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    User saveUser(User user);

    Optional<User> getUserByEmail(String email);

    List<User> getAllUsers();

    void deleteUser(Long id);
}
