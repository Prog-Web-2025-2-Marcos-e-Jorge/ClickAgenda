package br.iff.edu.ccc.clickagenda.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HorarioTrabalhoFormDTO {
    private List<HorarioTrabalhoRequestDTO> horarios;
}