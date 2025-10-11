package ar.com.huella.huella.securizer.controller;

import ar.com.huella.huella.securizer.dto.AuthResponse;
import ar.com.huella.huella.securizer.dto.LoginRequest;
import ar.com.huella.huella.securizer.dto.RegisterRequest;
import ar.com.huella.huella.securizer.dto.UpdatePasswordRequest;
import ar.com.huella.huella.securizer.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Usuario registrado correctamente. Revisa tu email para verificar la cuenta.");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) {
        authService.verifyAccount(token);
        return ResponseEntity.ok("Cuenta verificada correctamente.");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) {
        authService.resetPassword(email);
        return ResponseEntity.ok("Se ha enviado un email para restablecer la contraseña.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody UpdatePasswordRequest request) {
        authService.updatePassword(request.getToken(),request.getNewPassword() );
        return ResponseEntity.ok("Contraseña restablecida correctamente.");
    }
}
