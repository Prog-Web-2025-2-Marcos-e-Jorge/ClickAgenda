package br.iff.edu.ccc.clickagenda.controller.restapi;

import br.iff.edu.ccc.clickagenda.dto.LoginRequest;
import br.iff.edu.ccc.clickagenda.dto.LoginResponse;
import br.iff.edu.ccc.clickagenda.dto.RegisterRequest;
import br.iff.edu.ccc.clickagenda.dto.UserDTO;
import br.iff.edu.ccc.clickagenda.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Requisição de login para: {}", loginRequest.getEmail());
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        log.info("Requisição de registro para: {}", registerRequest.getEmail());
        LoginResponse response = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> getCurrentUser() {
        UserDTO userDTO = authService.getCurrentUser();
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth service is running!");
    }
}
