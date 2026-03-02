package br.iff.edu.ccc.clickagenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.iff.edu.ccc.clickagenda.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
}