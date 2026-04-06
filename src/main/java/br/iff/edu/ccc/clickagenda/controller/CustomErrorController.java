package br.iff.edu.ccc.clickagenda.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class CustomErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            String message = getErrorMessage(statusCode);

            log.warn("Erro HTTP {}: {}", statusCode, message);
            model.addAttribute("status", statusCode);
            model.addAttribute("message", message);

            // Retornar template específico ou genérico
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error/403";
            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                return "error/400";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/500";
            } else if (statusCode >= 500) {
                return "error/5xx";
            }
        }

        return "error/500";
    }

    private String getErrorMessage(int statusCode) {
        return switch (statusCode) {
            case 400 -> "Requisição inválida";
            case 401 -> "Não autenticado";
            case 403 -> "Acesso negado";
            case 404 -> "Página não encontrada";
            case 500 -> "Erro interno do servidor";
            case 502 -> "Gateway inválido";
            case 503 -> "Serviço indisponível";
            default -> "Erro desconhecido";
        };
    }
}
