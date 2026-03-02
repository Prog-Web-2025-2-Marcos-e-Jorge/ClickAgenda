package br.iff.edu.ccc.clickagenda.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.iff.edu.ccc.clickagenda.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByProfissionalId(Long id);

    @Query("SELECT a FROM Agendamento a WHERE a.profissional.id = :profissionalId " +
            "AND a.dataHora >= :inicioDia AND a.dataHora < :fimDia")
    List<Agendamento> findAgendamentosDoDia(
            @Param("profissionalId") Long profissionalId,
            @Param("inicioDia") LocalDateTime inicioDia,
            @Param("fimDia") LocalDateTime fimDia);
}