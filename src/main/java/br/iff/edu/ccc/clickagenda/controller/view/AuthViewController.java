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
import org.springframework.validation.BindingResult;
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
        model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login";
    }

    @PostMapping("/login")
    public String handleLogin(@Valid @ModelAttribute LoginRequest loginRequest,
            BindingResult bindingResult, Model model, HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {

        // Verificar erros de validação
        if (bindingResult.hasErrors()) {
            log.warn("Erros de validação no login: {}", bindingResult.getAllErrors());
            model.addAttribute("loginRequest", loginRequest);
            return "auth/login";
        }

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
            model.addAttribute("loginRequest", loginRequest);
            return "auth/login";
        }
    }

    @GetMapping("/registro")
    public String showRegisterPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String handleRegister(@Valid @ModelAttribute RegisterRequest registerRequest,
            BindingResult bindingResult, Model model, HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {

        // Verificar erros de validação
        if (bindingResult.hasErrors()) {
            log.warn("Erros de validação no registro: {}", bindingResult.getAllErrors());
            model.addAttribute("registerRequest", registerRequest);
            model.addAttribute("categorias", categoriaRepository.findAll());
            return "auth/registro";
        }

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
            model.addAttribute("registerRequest", registerRequest);
            model.addAttribute("categorias", categoriaRepository.findAll());
            return "auth/registro";
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
