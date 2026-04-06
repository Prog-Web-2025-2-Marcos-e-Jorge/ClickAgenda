package br.iff.edu.ccc.clickagenda.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ControllerAdvice
public class GlobalViewExceptionHandler {

    // Captura erros 404
    @ExceptionHandler(NoResourceFoundException.class)
    public ModelAndView handleNotFound(HttpServletRequest request, Exception ex) {
        log.warn("Página não encontrada: {}", request.getRequestURI());
        ModelAndView mav = new ModelAndView("error/404");
        return mav;
    }

    // Captura NotFoundException do sistema (recurso não encontrado no banco)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundException(HttpServletRequest request,
            NotFoundException ex) {
        log.warn("Recurso não encontrado: {}", ex.getMessage());
        ModelAndView mav = new ModelAndView("error/404");
        mav.addObject("mensagem", ex.getMessage());
        return mav;
    }

    // Captura erros genéricos (500)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericError(HttpServletRequest request, Exception ex) {
        log.error("Erro interno na requisição {}: {}", request.getRequestURI(), ex.getMessage());
        ModelAndView mav = new ModelAndView("error/500");
        mav.addObject("mensagem", ex.getMessage());
        return mav;
    }
}