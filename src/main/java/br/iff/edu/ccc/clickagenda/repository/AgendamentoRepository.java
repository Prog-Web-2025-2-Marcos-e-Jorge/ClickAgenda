package br.iff.edu.ccc.clickagenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.iff.edu.ccc.clickagenda.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

}
