package br.com.magnasistemas.apipessoa.exception.tratamento;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.magnasistemas.apipessoa.exception.CustomDataIntegrityException;

@RestControllerAdvice
public class CustomExceptionHandler {
	@ExceptionHandler(CustomDataIntegrityException.class)
	public ResponseEntity<List<ErroUnicidade>> tratarBuscar(CustomDataIntegrityException ex) {
		ErroUnicidade erroUnicidade = new ErroUnicidade(ex.getMessage());
		return ResponseEntity.badRequest().body(List.of(erroUnicidade));
	}
 
	public record ErroUnicidade(String mensagem) {
		public ErroUnicidade(FieldError erro) {
			this(erro.getDefaultMessage());
		}
	}
}
