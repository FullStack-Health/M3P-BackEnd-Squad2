package br.com.pvv.senai.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvicer {

	static final Logger log = LoggerFactory.getLogger(ExceptionAdvicer.class);

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity excessao1(NullPointerException ex) {
		log.error("NullPointerException");
		log.error(ex.getMessage());
		return ResponseEntity.status(500).build();
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity excessao2(HttpMessageNotReadableException ex) {
		log.error("HttpMessageNotReadableException");
		log.error(ex.getMessage());
		return ResponseEntity.status(500).build();
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity excessao3(NumberFormatException ex) {
		log.error("NumberFormatException");
		log.error(ex.getMessage());
		return ResponseEntity.status(500).build();
	}

	@ExceptionHandler(PacienteUserNotFoundException.class)
	public ResponseEntity excessao4(PacienteUserNotFoundException ex) {
		log.error("PacienteUserNotFoundException");
		log.error(ex.getMessage());
		return ResponseEntity.status(406).body("Paciente sem usuário cadastrado");
	}

	@ExceptionHandler(DtoToEntityException.class)
	public ResponseEntity excessao4(DtoToEntityException ex) {
		log.error("DtoToEntityException");
		log.error(ex.getMessage());
		return ResponseEntity.status(500).build();
	}

	@ExceptionHandler(CampoInvalidoException.class)
	public ResponseEntity excessao4(CampoInvalidoException ex) {
		log.error("CampoInvalidoException");
		log.error(ex.getMessage());
		return ResponseEntity.status(406).body("Campo com dados inválidos | " + ex.getMessage());
	}

	@ExceptionHandler(EmailInvalidoException.class)
	public ResponseEntity excessao5(EmailInvalidoException ex) {
		log.error("EmailInvalidoException");
		log.error(ex.getMessage());
		return ResponseEntity.status(406).body("E-mail em formato inválido.");
	}

	@ExceptionHandler(EmailExistenteException.class)
	public ResponseEntity excessao6(EmailExistenteException ex) {
		log.error("EmailExistenteException");
		log.error(ex.getMessage());
		return ResponseEntity.status(406).body("E-mail já cadastrado.");
	}

	@ExceptionHandler(NotRequiredByProjectException.class)
	public ResponseEntity excessao7(NotRequiredByProjectException ex) {
		log.error("NotRequiredByProjectException");
		log.error(ex.getMessage());
		return ResponseEntity.status(417).body("Parece que um método não requerido em projeto foi utilizado.");
	}

	@ExceptionHandler(UnauthorizationException.class)
	public ResponseEntity excessao8(UnauthorizationException ex) {
		log.error("UnauthorizationException");
		log.error(ex.getMessage());
		return ResponseEntity.status(401).body("Não autorizado.");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity excessao8(MethodArgumentNotValidException ex) {
		log.error("MethodArgumentNotValidException");
		log.error(ex.getMessage());
		return ResponseEntity.status(400).body("Requisição inválida em razão de campo inválido: " + ex.getMessage());
	}
}
