package br.iff.edu.ccc.clickagenda.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.iff.edu.ccc.clickagenda.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT c FROM Categoria c JOIN c.profissionais p WHERE p.id = :profissionalId")
    List<Categoria> findByProfissionalId(@Param("profissionalId") Long profissionalId);
}
