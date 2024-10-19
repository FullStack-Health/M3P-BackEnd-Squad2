package br.com.pvv.senai.controller;

import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pvv.senai.controller.filter.IFilter;
import br.com.pvv.senai.entity.Usuario;
import br.com.pvv.senai.enums.Perfil;
import br.com.pvv.senai.exceptions.DtoToEntityException;
import br.com.pvv.senai.exceptions.EmailViolationExistentException;
import br.com.pvv.senai.model.dto.UsuarioDto;
import br.com.pvv.senai.security.UsuarioService;
import br.com.pvv.senai.service.GenericService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("usuario")
public class UsuarioController extends GenericController<UsuarioDto, Usuario> {

	@Autowired
	private UsuarioService service; 
	
	@Override
	public GenericService<Usuario> getService() {
		return service;
	}

	@Override
	public IFilter<Usuario> filterBuilder(Map<String, String> params) throws Exception {
		throw new UnsupportedOperationException();
	}
	
	@PostMapping("pre-registro")
	public ResponseEntity<Usuario> register(@RequestBody @Valid UsuarioDto model) throws MethodArgumentNotValidException, DtoToEntityException, BadRequestException, EmailViolationExistentException {
		if (model.getPerfil() != Perfil.MEDICO && model.getPerfil() != Perfil.ADMIN) throw new BadRequestException("Perfil não autorizado.");
		
		var entity = model.makeEntity();
		try {
			entity = service.create(entity);
		} catch (DataIntegrityViolationException ex) {
			throw new EmailViolationExistentException();
		}
		entity.setPassword(null);
		return ResponseEntity.status(201).body(entity);
	}

}
