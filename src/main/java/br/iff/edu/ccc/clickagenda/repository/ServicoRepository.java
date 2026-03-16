package br.iff.edu.ccc.clickagenda.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.iff.edu.ccc.clickagenda.model.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    List<Servico> findByProfissionalId(Long profissionalId);

    List<Servico> findByCategoriaId(Long categoriaId);

    @Query("SELECT s FROM Servico s WHERE s.nome LIKE %:nome%")
    List<Servico> findByNomeContaining(@Param("nome") String nome);
}
