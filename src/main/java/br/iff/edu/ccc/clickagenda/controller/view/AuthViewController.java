package br.iff.edu.ccc.clickagenda.controller.view;

import br.iff.edu.ccc.clickagenda.dto.LoginRequest;
import br.iff.edu.ccc.clickagenda.dto.LoginResponse;
import br.iff.edu.ccc.clickagenda.dto.RegisterRequest;
import br.iff.edu.ccc.clickagenda.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class AuthViewController {

    private final AuthService authService;

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Email ou senha inválidos");
        }
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@Valid @ModelAttribute LoginRequest loginRequest,
            Model model, HttpSession session) {
        try {
            log.info("Login solicitado para: {}", loginRequest.getEmail());

            LoginResponse response = authService.login(loginRequest);

            session.setAttribute("token", response.getToken());
            session.setAttribute("usuarioId", response.getId());
            session.setAttribute("usuarioEmail", response.getEmail());
            session.setAttribute("usuarioNome", response.getNome());
            session.setAttribute("usuarioPerfil", response.getPerfil());

            log.info("Login bem-sucedido para: {}", loginRequest.getEmail());

            return "redirect:/";

        } catch (Exception ex) {
            log.warn("Falha no login: {}", ex.getMessage());
            model.addAttribute("error", "Email ou senha inválidos");
            return "login";
        }
    }

    @GetMapping("/registro")
    public String showRegisterPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "registro";
    }

    @PostMapping("/registro")
    public String handleRegister(@Valid @ModelAttribute RegisterRequest registerRequest,
            Model model, HttpSession session) {
        try {
            log.info("Registro solicitado para: {}", registerRequest.getEmail());

            LoginResponse response = authService.register(registerRequest);

            session.setAttribute("token", response.getToken());
            session.setAttribute("usuarioId", response.getId());
            session.setAttribute("usuarioEmail", response.getEmail());
            session.setAttribute("usuarioNome", response.getNome());
            session.setAttribute("usuarioPerfil", response.getPerfil());

            log.info("Registro bem-sucedido para: {}", registerRequest.getEmail());

            return "redirect:/";

        } catch (RuntimeException ex) {
            log.warn("Falha no registro: {}", ex.getMessage());
            model.addAttribute("error", ex.getMessage());
            return "registro";
        }
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        log.info("Logout solicitado");
        session.invalidate();
        return "redirect:/login";
    }
}
