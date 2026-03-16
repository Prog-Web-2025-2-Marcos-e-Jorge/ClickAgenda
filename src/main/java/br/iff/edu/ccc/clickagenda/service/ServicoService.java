package br.iff.edu.ccc.clickagenda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.iff.edu.ccc.clickagenda.dto.request.ServicoRequestDTO;
import br.iff.edu.ccc.clickagenda.dto.response.ServicoResponseDTO;
import br.iff.edu.ccc.clickagenda.exception.BadRequestException;
import br.iff.edu.ccc.clickagenda.exception.NotFoundException;
import br.iff.edu.ccc.clickagenda.model.Categoria;
import br.iff.edu.ccc.clickagenda.model.Profissional;
import br.iff.edu.ccc.clickagenda.model.Servico;
import br.iff.edu.ccc.clickagenda.repository.CategoriaRepository;
import br.iff.edu.ccc.clickagenda.repository.ProfissionalRepository;
import br.iff.edu.ccc.clickagenda.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final ProfissionalRepository profissionalRepository;
    private final CategoriaRepository categoriaRepository;

    @Transactional
    public ServicoResponseDTO salvar(ServicoRequestDTO dto) {
        Profissional profissional = profissionalRepository.findById(dto.getProfissionalId())
                .orElseThrow(() -> new NotFoundException("Profissional não encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        // Validar se profissional atende essa categoria
        if (profissional.getCategorias() == null ||
                profissional.getCategorias().stream()
                        .noneMatch(c -> c.getId().equals(categoria.getId()))) {
            throw new BadRequestException(
                    "O profissional não está habilitado para atender a categoria: " + categoria.getNome());
        }

        Servico servico = new Servico();
        servico.setNome(dto.getNome());
        servico.setValor(dto.getValor());
        servico.setDuracaoMinutos(dto.getDuracaoMinutos());
        servico.setProfissional(profissional);
        servico.setCategoria(categoria);

        Servico salvo = servicoRepository.save(servico);
        return converterResponseDTO(salvo);
    }

    public ServicoResponseDTO buscarPorId(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Serviço não encontrado com ID: " + id));
        return converterResponseDTO(servico);
    }

    public List<ServicoResponseDTO> listarTodos() {
        return servicoRepository.findAll().stream()
                .map(this::converterResponseDTO)
                .toList();
    }

    @Transactional
    public ServicoResponseDTO atualizar(Long id, ServicoRequestDTO dto) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Serviço não encontrado com ID: " + id));

        if (dto.getNome() != null) {
            servico.setNome(dto.getNome());
        }
        if (dto.getValor() != null) {
            servico.setValor(dto.getValor());
        }
        if (dto.getDuracaoMinutos() != null) {
            servico.setDuracaoMinutos(dto.getDuracaoMinutos());
        }
        if (dto.getProfissionalId() != null) {
            Profissional profissional = profissionalRepository.findById(dto.getProfissionalId())
                    .orElseThrow(() -> new NotFoundException("Profissional não encontrado"));
            servico.setProfissional(profissional);
        }
        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

            // Validar se o profissional atende essa categoria
            Profissional profissionalAtual = servico.getProfissional();
            if (profissionalAtual.getCategorias() == null ||
                    profissionalAtual.getCategorias().stream()
                            .noneMatch(c -> c.getId().equals(categoria.getId()))) {
                throw new BadRequestException(
                        "O profissional não está habilitado para atender a categoria: " + categoria.getNome());
            }

            servico.setCategoria(categoria);
        }

        Servico atualizado = servicoRepository.save(servico);
        return converterResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Serviço não encontrado com ID: " + id));
        servicoRepository.delete(servico);
    }

    private ServicoResponseDTO converterResponseDTO(Servico servico) {
        ServicoResponseDTO dto = new ServicoResponseDTO();
        dto.setId(servico.getId());
        dto.setNome(servico.getNome());
        dto.setValor(servico.getValor());
        dto.setDuracaoMinutos(servico.getDuracaoMinutos());
        return dto;
    }
}