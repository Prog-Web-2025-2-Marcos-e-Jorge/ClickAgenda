package br.iff.edu.ccc.clickagenda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.iff.edu.ccc.clickagenda.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByProfissionalId(Long id);

}
