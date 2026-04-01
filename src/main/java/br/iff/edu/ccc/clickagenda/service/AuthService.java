package br.iff.edu.ccc.clickagenda.service;

import br.iff.edu.ccc.clickagenda.dto.LoginRequest;
import br.iff.edu.ccc.clickagenda.dto.LoginResponse;
import br.iff.edu.ccc.clickagenda.dto.RegisterRequest;
import br.iff.edu.ccc.clickagenda.dto.UserDTO;
import br.iff.edu.ccc.clickagenda.enums.Perfil;
import br.iff.edu.ccc.clickagenda.model.Cliente;
import br.iff.edu.ccc.clickagenda.model.Usuario;
import br.iff.edu.ccc.clickagenda.repository.UsuarioRepository;
import br.iff.edu.ccc.clickagenda.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest loginRequest) {
        log.info("Tentativa de login para o email: {}", loginRequest.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getSenha()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        log.info("Login realizado com sucesso para: {}", loginRequest.getEmail());

        return new LoginResponse(
                token,
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNome(),
                usuario.getPerfil().toString());
    }

    @Transactional
    public LoginResponse register(RegisterRequest registerRequest) {
        log.info("Tentativa de registro para o email: {}", registerRequest.getEmail());

        if (usuarioRepository.existsByEmail(registerRequest.getEmail())) {
            log.warn("Email já registrado: {}", registerRequest.getEmail());
            throw new RuntimeException("Email já está registrado");
        }

        if (!registerRequest.getSenha().equals(registerRequest.getSenhaConfirmacao())) {
            log.warn("Senhas não coincidem para o email: {}", registerRequest.getEmail());
            throw new RuntimeException("Login ou senha inválidos.");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(registerRequest.getNome());
        cliente.setCpf(registerRequest.getCpf());
        cliente.setEmail(registerRequest.getEmail());
        cliente.setTelefone(registerRequest.getTelefone());
        cliente.setSenha(passwordEncoder.encode(registerRequest.getSenha()));
        cliente.setPerfil(Perfil.CLIENTE);
        cliente.setAtivo(true);

        Usuario usuarioSalvo = usuarioRepository.save(cliente);

        log.info("Novo usuário registrado com sucesso: {} com ID: {}", registerRequest.getEmail(),
                usuarioSalvo.getId());

        return login(new LoginRequest(registerRequest.getEmail(), registerRequest.getSenha()));
    }

    public UserDTO convertToDTO(Usuario usuario) {
        UserDTO dto = new UserDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setCpf(usuario.getCpf());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setPerfil(usuario.getPerfil().toString());
        dto.setAtivo(usuario.isAtivo());
        return dto;
    }

    @Transactional(readOnly = true)
    public UserDTO getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return convertToDTO(usuario);
    }
}
