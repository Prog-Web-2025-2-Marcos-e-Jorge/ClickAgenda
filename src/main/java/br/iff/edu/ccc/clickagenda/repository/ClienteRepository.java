package br.iff.edu.ccc.clickagenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.iff.edu.ccc.clickagenda.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
