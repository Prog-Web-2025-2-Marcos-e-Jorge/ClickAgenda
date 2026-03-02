package br.iff.edu.ccc.clickagenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.iff.edu.ccc.clickagenda.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
