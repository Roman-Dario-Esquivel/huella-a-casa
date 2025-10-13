package ar.com.huella.huella.securizer.service.implementation;

import ar.com.huella.huella.securizer.dto.AuthResponse;
import ar.com.huella.huella.securizer.dto.LoginRequest;
import ar.com.huella.huella.securizer.dto.RegisterRequest;
import ar.com.huella.huella.securizer.entity.PasswordResetToken;
import ar.com.huella.huella.securizer.entity.Role;
import ar.com.huella.huella.securizer.entity.RoleEnum;
import ar.com.huella.huella.securizer.entity.User;
import ar.com.huella.huella.securizer.entity.VerificationToken;
import ar.com.huella.huella.securizer.repository.IPasswordResetTokenRepository;
import ar.com.huella.huella.securizer.repository.IRoleRepository;
import ar.com.huella.huella.securizer.repository.IUserRepository;
import ar.com.huella.huella.securizer.repository.IVerificationTokenRepository;
import ar.com.huella.huella.securizer.service.IAuthService;
import ar.com.huella.huella.securizer.service.IEmailService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthService implements IAuthService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IPasswordResetTokenRepository tokenRepository;
    @Autowired
    private IEmailService emailService;
    @Autowired
    private IVerificationTokenRepository verificationTokenRepository;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(false); // 🚨 inicialmente deshabilitado
        Role userRole = roleRepository.findByRoleEnum(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("El rol ROLE_USER no existe"));
        user.getRoles().add(userRole);
        userRepository.save(user);

        // Crear token de verificación
        String token = java.util.UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusHours(24)) // expira en 24hs
                .build();
        verificationTokenRepository.save(verificationToken);

        // Enviar correo
        emailService.sendVerificationEmail(user.getEmail(), token);

        // (no devolvemos AuthResponse todavía, hasta que confirme)
        return null;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas"); // mensaje genérico
        }
        String token = jwtService.generateToken(user.getEmail(), new HashMap<>());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        return new AuthResponse(token, refreshToken);
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        String email = jwtService.extractUsername(refreshToken);

        if (!jwtService.isTokenValid(refreshToken, email)) {
            throw new RuntimeException("Refresh token inválido");
        }

        String newAccessToken = jwtService.generateToken(email, new HashMap<>());
        return new AuthResponse(newAccessToken, refreshToken);
    }

    @Override
    public void resetPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = java.util.UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(30)) // 30 min
                .build();

        tokenRepository.save(resetToken);

        // Enviar email
        emailService.sendPasswordResetEmail(user.getEmail(), token);
    }

    @Override
    public void updatePassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El token ha expirado");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        tokenRepository.deleteByToken(token);
    }

    @Override
    public void verifyAccount(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El token ha expirado");
        }

        User user = verificationToken.getUser();
        user.setEnabled(true); // 🚨 habilitar usuario
        userRepository.save(user);

        verificationTokenRepository.deleteByToken(token);
    }
}
