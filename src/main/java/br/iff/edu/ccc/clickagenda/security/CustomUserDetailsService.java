package br.iff.edu.ccc.clickagenda.security;

import br.iff.edu.ccc.clickagenda.model.Usuario;
import br.iff.edu.ccc.clickagenda.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));

        if (!usuario.isAtivo()) {
            log.warn("Tentativa de login com usuário inativo: {}", email);
            throw new UsernameNotFoundException("Usuário está inativo");
        }

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities(new SimpleGrantedAuthority("ROLE_" + usuario.getPerfil().name()))
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .disabled(!usuario.isAtivo())
                .build();
    }

    public Usuario loadUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com ID: " + id));
    }

    public Usuario loadUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));
    }
}
