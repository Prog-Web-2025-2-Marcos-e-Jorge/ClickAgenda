package br.iff.edu.ccc.clickagenda.controller.restapi;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class RestMainApiController {

    @GetMapping()
    public ResponseEntity<Map<String, Object>> getApiHome() {
        Map<String, Object> resposta = new LinkedHashMap<>();
        resposta.put("mensagem", "Bem vindo à API REST do MultiAgenda!");
        resposta.put("versao", "1.0.0");
        resposta.put("status", "Online");

        Map<String, String> recursos = new LinkedHashMap<>();
        recursos.put("Profissionais", "/api/profissional");
        recursos.put("Clientes", "/api/cliente");
        recursos.put("Serviços", "/api/servico");
        recursos.put("Categorias", "/api/categoria");
        recursos.put("Agendamentos", "/api/agendamento");
        recursos.put("Horários de Trabalho", "/api/horario-trabalho");

        resposta.put("recursos", recursos);

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "servico", "MultiAgenda API"));
    }
}
