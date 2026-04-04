package br.iff.edu.ccc.clickagenda.controller.view;

import br.iff.edu.ccc.clickagenda.dto.request.LoginRequest;
import br.iff.edu.ccc.clickagenda.dto.request.RegisterRequest;
import br.iff.edu.ccc.clickagenda.dto.response.LoginResponse;
import br.iff.edu.ccc.clickagenda.repository.CategoriaRepository;
import br.iff.edu.ccc.clickagenda.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class AuthViewController {

    private final AuthService authService;
    private final CategoriaRepository categoriaRepository;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Email ou senha inválidos");
        }
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@Valid @ModelAttribute LoginRequest loginRequest,
            Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("Login solicitado para: {}", loginRequest.getEmail());

            LoginResponse loginResponse = authService.login(loginRequest);

            session.setAttribute("token", loginResponse.getToken());
            session.setAttribute("usuarioId", loginResponse.getId());
            session.setAttribute("usuarioEmail", loginResponse.getEmail());
            session.setAttribute("usuarioNome", loginResponse.getNome());
            session.setAttribute("usuarioPerfil", loginResponse.getPerfil());

            // Autenticar e SALVAR NA SESSÃO
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getSenha()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Persistir autenticação na sessão HTTP explicitamente
            securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);

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
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "registro";
    }

    @PostMapping("/registro")
    public String handleRegister(@Valid @ModelAttribute RegisterRequest registerRequest,
            Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("Registro solicitado para: {}", registerRequest.getEmail());

            LoginResponse loginResponse = authService.register(registerRequest);

            session.setAttribute("token", loginResponse.getToken());
            session.setAttribute("usuarioId", loginResponse.getId());
            session.setAttribute("usuarioEmail", loginResponse.getEmail());
            session.setAttribute("usuarioNome", loginResponse.getNome());
            session.setAttribute("usuarioPerfil", loginResponse.getPerfil());

            // Autenticar e SALVAR NA SESSÃO
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            registerRequest.getEmail(),
                            registerRequest.getSenha()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Persistir autenticação na sessão HTTP explicitamente
            securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);

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
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }
}
