package br.iff.edu.ccc.clickagenda.config;

import br.iff.edu.ccc.clickagenda.model.Admin;
import br.iff.edu.ccc.clickagenda.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeAdminUser();
    }

    private void initializeAdminUser() {
        String adminEmail = "admin@clickagenda.com.br";

        if (usuarioRepository.findByEmail(adminEmail).isPresent()) {
            log.info("Usuário admin já existe no sistema");
            return;
        }

        Admin admin = new Admin();
        admin.setNome("Administrador");
        admin.setCpf("11144477735");
        admin.setEmail(adminEmail);
        admin.setTelefone("11999999999");
        admin.setSenha(passwordEncoder.encode("AdminPass"));
        admin.setAtivo(true);

        usuarioRepository.save(admin);
        log.info("✓ Usuário admin criado com sucesso!");
        log.info("  Email: admin@clickagenda.com.br");
        log.info("  Senha: AdminPass");
    }
}
