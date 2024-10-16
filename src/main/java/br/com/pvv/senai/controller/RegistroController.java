package br.com.pvv.senai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pvv.senai.exceptions.DtoToEntityException;
import br.com.pvv.senai.exceptions.EmailInvalidoException;
import br.com.pvv.senai.exceptions.EmailExistenteException;
import br.com.pvv.senai.model.dto.UsuarioDto;
import br.com.pvv.senai.security.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class RegistroController {

	@Autowired
	private UsuarioService service;
	
	@PostMapping
	public ResponseEntity register(@RequestBody UsuarioDto model) throws EmailInvalidoException, EmailExistenteException, DtoToEntityException {
		if (model.getEmail() == null) throw new EmailInvalidoException();
		if (service.has(model.getEmail())) throw new EmailExistenteException();
		
		model.setPassword(new BCryptPasswordEncoder().encode(model.getPassword()));
		
		var entity = service.create(model.makeEntity());
		return ResponseEntity.ok().build();
	}
	
}
