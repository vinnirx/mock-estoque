package br.com.estoque.exception;

import br.com.estoque.dto.EstoqueResposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<EstoqueResposta> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Erro encontrado:", ex);

        EstoqueResposta resposta = new EstoqueResposta();
        resposta.setMensagem(ex.getMessage());
        return new ResponseEntity<>(resposta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<EstoqueResposta> handleGenericException(Exception ex) {
        logger.error("Erro interno do servidor:", ex);

        EstoqueResposta resposta = new EstoqueResposta();
        resposta.setMensagem("Ocorreu um erro interno no servidor.");
        return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}