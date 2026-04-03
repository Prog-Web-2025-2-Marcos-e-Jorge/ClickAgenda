package br.iff.edu.ccc.clickagenda.service;

import br.iff.edu.ccc.clickagenda.dto.LoginRequest;
import br.iff.edu.ccc.clickagenda.dto.LoginResponse;
import br.iff.edu.ccc.clickagenda.dto.RegisterRequest;
import br.iff.edu.ccc.clickagenda.dto.UserDTO;
import br.iff.edu.ccc.clickagenda.enums.Perfil;
import br.iff.edu.ccc.clickagenda.model.Categoria;
import br.iff.edu.ccc.clickagenda.model.Cliente;
import br.iff.edu.ccc.clickagenda.model.Profissional;
import br.iff.edu.ccc.clickagenda.model.Usuario;
import br.iff.edu.ccc.clickagenda.repository.CategoriaRepository;
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
    private final CategoriaRepository categoriaRepository;
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
            throw new RuntimeException("Senhas não coincidem");
        }

        // Validar userType
        if (registerRequest.getUserType() == null || registerRequest.getUserType().isEmpty()) {
            log.warn("Tipo de usuário não informado para: {}", registerRequest.getEmail());
            throw new RuntimeException("Tipo de usuário não pode ser vazio");
        }

        String userType = registerRequest.getUserType().toUpperCase();
        if (!userType.equals("CLIENTE") && !userType.equals("PROFISSIONAL")) {
            log.warn("Tipo de usuário inválido: {} para {}", userType, registerRequest.getEmail());
            throw new RuntimeException("Tipo de usuário inválido. Deve ser 'CLIENTE' ou 'PROFISSIONAL'");
        }

        Usuario usuario;

        if ("PROFISSIONAL".equals(userType)) {
            // Validar categorias para Profissional
            if (registerRequest.getCategoriaIds() == null || registerRequest.getCategoriaIds().isEmpty()) {
                log.warn("Nenhuma categoria informada para profissional: {}", registerRequest.getEmail());
                throw new RuntimeException("Profissional deve selecionar pelo menos uma categoria");
            }

            Profissional profissional = new Profissional();
            profissional.setNome(registerRequest.getNome());
            profissional.setCpf(registerRequest.getCpf());
            profissional.setEmail(registerRequest.getEmail());
            profissional.setTelefone(registerRequest.getTelefone());
            profissional.setSenha(passwordEncoder.encode(registerRequest.getSenha()));
            profissional.setPerfil(Perfil.PROFISSIONAL);
            profissional.setAtivo(true);

            // Buscar e adicionar categorias
            java.util.List<Categoria> categorias = categoriaRepository.findAllById(registerRequest.getCategoriaIds());
            profissional.setCategorias(categorias);

            usuario = profissional;
            log.info("Novo Profissional registrado: {} com {} categorias", registerRequest.getEmail(),
                    categorias.size());

        } else {
            // Cliente
            Cliente cliente = new Cliente();
            cliente.setNome(registerRequest.getNome());
            cliente.setCpf(registerRequest.getCpf());
            cliente.setEmail(registerRequest.getEmail());
            cliente.setTelefone(registerRequest.getTelefone());
            cliente.setSenha(passwordEncoder.encode(registerRequest.getSenha()));
            cliente.setPerfil(Perfil.CLIENTE);
            cliente.setAtivo(true);

            usuario = cliente;
            log.info("Novo Cliente registrado: {}", registerRequest.getEmail());
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

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
