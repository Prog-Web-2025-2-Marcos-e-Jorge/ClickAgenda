package br.iff.edu.ccc.clickagenda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.iff.edu.ccc.clickagenda.dto.request.ProfissionalRequestDTO;
import br.iff.edu.ccc.clickagenda.dto.response.ProfissionalResponseDTO;
import br.iff.edu.ccc.clickagenda.exception.BadRequestException;
import br.iff.edu.ccc.clickagenda.exception.NotFoundException;
import br.iff.edu.ccc.clickagenda.model.Categoria;
import br.iff.edu.ccc.clickagenda.model.Profissional;
import br.iff.edu.ccc.clickagenda.repository.CategoriaRepository;
import br.iff.edu.ccc.clickagenda.repository.ProfissionalRepository;
import br.iff.edu.ccc.clickagenda.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    @Transactional
    public ProfissionalResponseDTO salvar(ProfissionalRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("O e-mail informado já está em uso na plataforma.");
        }

        Profissional profissional = new Profissional();
        profissional.setNome(dto.getNome());
        profissional.setCpf(dto.getCpf());
        profissional.setEmail(dto.getEmail());
        profissional.setTelefone(dto.getTelefone());
        profissional.setSenha(dto.getSenha()); // TODO: adicionar criptografia de senha

        Profissional salvo = profissionalRepository.save(profissional);
        return converterResponseDTO(salvo);
    }

    public ProfissionalResponseDTO buscarPorId(Long id) {
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profissional não encontrado com ID: " + id));
        return converterResponseDTO(profissional);
    }

    public List<ProfissionalResponseDTO> listarTodos() {
        return profissionalRepository.findAll().stream()
                .map(this::converterResponseDTO)
                .toList();
    }

    @Transactional
    public ProfissionalResponseDTO atualizar(Long id, ProfissionalRequestDTO dto) {
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profissional não encontrado com ID: " + id));

        if (dto.getNome() != null) {
            profissional.setNome(dto.getNome());
        }
        if (dto.getEmail() != null && !dto.getEmail().equals(profissional.getEmail())) {
            if (usuarioRepository.existsByEmail(dto.getEmail())) {
                throw new BadRequestException("O e-mail informado já está em uso na plataforma.");
            }
            profissional.setEmail(dto.getEmail());
        }
        if (dto.getTelefone() != null) {
            profissional.setTelefone(dto.getTelefone());
        }
        if (dto.getSenha() != null) {
            profissional.setSenha(dto.getSenha()); // TODO: adicionar criptografia de senha
        }

        Profissional atualizado = profissionalRepository.save(profissional);
        return converterResponseDTO(atualizado);
    }

    @Transactional
    public ProfissionalResponseDTO adicionarCategoria(Long profId, Long catId) {
        Profissional profissional = profissionalRepository.findById(profId)
                .orElseThrow(() -> new NotFoundException("Profissional não encontrado com ID: " + profId));
        Categoria categoria = categoriaRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada com ID: " + catId));

        // Inicializar lista se nula
        if (profissional.getCategorias() == null) {
            profissional.setCategorias(List.of());
        }

        // Verificar se categoria já está associada
        if (profissional.getCategorias().stream().anyMatch(c -> c.getId().equals(categoria.getId()))) {
            throw new BadRequestException("Profissional já atende à categoria indicada!");
        }

        // Adicionar categoria
        List<Categoria> categorias = profissional.getCategorias();
        categorias.add(categoria);

        Profissional atualizado = profissionalRepository.save(profissional);
        return converterResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profissional não encontrado com ID: " + id));
        profissionalRepository.delete(profissional);
    }

    private ProfissionalResponseDTO converterResponseDTO(Profissional profissional) {
        ProfissionalResponseDTO dto = new ProfissionalResponseDTO();
        dto.setId(profissional.getId());
        dto.setNome(profissional.getNome());
        dto.setCpf(profissional.getCpf());
        dto.setEmail(profissional.getEmail());
        dto.setTelefone(profissional.getTelefone());

        // Converter horários de trabalho se existirem
        if (profissional.getHorariosTrabalho() != null) {
            dto.setHorariosTrabalho(
                    profissional.getHorariosTrabalho().stream()
                            .map(h -> {
                                var horarioDTO = new br.iff.edu.ccc.clickagenda.dto.response.HorarioTrabalhoResponseDTO();
                                horarioDTO.setId(h.getId());
                                horarioDTO.setDiaSemana(h.getDiaSemana());
                                horarioDTO.setHorarioInicio(h.getHorarioInicio());
                                horarioDTO.setHorarioFim(h.getHorarioFim());
                                horarioDTO.setDiaFolga(h.isDiaFolga());
                                horarioDTO.setProfissionalId(h.getProfissional().getId());
                                horarioDTO.setProfissionalNome(h.getProfissional().getNome());
                                return horarioDTO;
                            })
                            .toList());
        }

        // Converter categorias se existirem
        if (profissional.getCategorias() != null) {
            dto.setCategorias(
                    profissional.getCategorias().stream()
                            .map(c -> {
                                var categoriaDTO = new br.iff.edu.ccc.clickagenda.dto.response.CategoriaResponseDTO();
                                categoriaDTO.setId(c.getId());
                                categoriaDTO.setNome(c.getNome());
                                return categoriaDTO;
                            })
                            .toList());
        }

        if (profissional.getServicos() != null) {
            dto.setServicos(
                    profissional.getServicos().stream()
                            .map(s -> {
                                var servicoDTO = new br.iff.edu.ccc.clickagenda.dto.response.ServicoResponseDTO();
                                servicoDTO.setId(s.getId());
                                servicoDTO.setNome(s.getNome());
                                servicoDTO.setValor(s.getValor());
                                servicoDTO.setDuracaoMinutos(s.getDuracaoMinutos());
                                return servicoDTO;
                            })
                            .toList());
        }

        return dto;
    }
}