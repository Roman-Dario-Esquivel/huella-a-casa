package ar.com.huella.huella.securizer.service;

import ar.com.huella.huella.securizer.dto.AuthResponse;
import ar.com.huella.huella.securizer.dto.LoginRequest;
import ar.com.huella.huella.securizer.dto.RegisterRequest;

public interface IAuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(String refreshToken);

    void resetPassword(String email);

    public void updatePassword(String token, String newPassword);

    public void verifyAccount(String token);
}
