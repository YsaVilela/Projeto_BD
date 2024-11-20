package br.com.magnasistemas.apipessoa.exception;

public class InvalidContentException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidContentException(String mensage) {
        super(mensage);
    }
}
