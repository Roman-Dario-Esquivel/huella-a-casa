package ar.com.huella.huella.securizer.controller;

import ar.com.huella.huella.securizer.dto.AuthResponse;
import ar.com.huella.huella.securizer.dto.LoginRequest;
import ar.com.huella.huella.securizer.dto.RegisterRequest;
import ar.com.huella.huella.securizer.dto.UpdatePasswordRequest;
import ar.com.huella.huella.securizer.service.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autorizacion", description = "Operaciones de autorizacion, login, verificacion, registracion, olvide de contraseña, reset.")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Metodo de registro ", description = "Metodo que sirve para registrar un usuarios.")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Usuario registrado correctamente. Revisa tu email para verificar la cuenta.");
    }

    @PostMapping("/login")
    @Operation(summary = "Metodo de login ", description = "Metodo que sirve para logear y devuelve un token.")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    @Operation(summary = "Metodo de verificacion ", description = "Metodo que sirve para verificar cuenta inicial.")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) {
        authService.verifyAccount(token);
        return ResponseEntity.ok("Cuenta verificada correctamente.");
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Metodo de olvide contraseña ", description = "Metodo que sirve para indicar que se olvido la contraseña.")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) {
        authService.resetPassword(email);
        return ResponseEntity.ok("Se ha enviado un email para restablecer la contraseña.");
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Metodo resetiar contraseña", description = "Metodo que sirve para resetear la contraseña en base al token de olvide contraseña.")
    public ResponseEntity<String> resetPassword(@RequestBody UpdatePasswordRequest request) {
        authService.updatePassword(request.getToken(),request.getNewPassword() );
        return ResponseEntity.ok("Contraseña restablecida correctamente.");
    }
}
