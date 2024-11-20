package br.com.magnasistemas.apipessoa.exception.tratamento;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.magnasistemas.apipessoa.exception.InvalidDataException;

@RestControllerAdvice
public class InvalidDataHandler {
    @ExceptionHandler(InvalidDataException.class) 
    public ResponseEntity<List<DadosInvalidos>> tratarDadosInvalidos(InvalidDataException ex) {
        DadosInvalidos dadosInvalidos = new DadosInvalidos(ex.getMessage());
        return ResponseEntity.badRequest().body(List.of(dadosInvalidos));
    }

    public record DadosInvalidos(String mensagem) {
        public DadosInvalidos(FieldError erro) {
            this(erro.getDefaultMessage());
        }
    }

}
