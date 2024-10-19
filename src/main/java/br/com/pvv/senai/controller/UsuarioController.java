package br.com.pvv.senai.controller;

import java.security.Principal;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pvv.senai.controller.filter.IFilter;
import br.com.pvv.senai.controller.filter.UsuarioFilter;
import br.com.pvv.senai.entity.Usuario;
import br.com.pvv.senai.enums.Perfil;
import br.com.pvv.senai.exceptions.DtoToEntityException;
import br.com.pvv.senai.exceptions.EmailViolationExistentException;
import br.com.pvv.senai.exceptions.NotAuthorizedException;
import br.com.pvv.senai.exceptions.UnauthorizationException;
import br.com.pvv.senai.exceptions.UsuarioNotFoundException;
import br.com.pvv.senai.model.dto.UsuarioDto;
import br.com.pvv.senai.model.dto.UsuarioDtoMinimal;
import br.com.pvv.senai.security.TokenService;
import br.com.pvv.senai.security.UsuarioService;
import br.com.pvv.senai.service.GenericService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends GenericController<UsuarioDto, Usuario> {

	@Autowired
	private TokenService jwtService;

	@Autowired
	private UsuarioService service;

	@Override
	public GenericService<Usuario> getService() {
		return service;
	}

	@Override
	public IFilter<Usuario> filterBuilder(Map<String, String> params) throws Exception {
		return new UsuarioFilter(params);
	}
	
	@GetMapping("/me")
	public ResponseEntity me(Principal principal) {
		var usuario = service.findByEmail(principal.getName());
		return ResponseEntity.ok(usuario);
	}

	@PostMapping("pre-registro")
	public ResponseEntity<Usuario> register(@RequestBody @Valid UsuarioDtoMinimal model)
			throws MethodArgumentNotValidException, DtoToEntityException, BadRequestException,
			EmailViolationExistentException {
		if (model.getPerfil() != Perfil.MEDICO && model.getPerfil() != Perfil.ADMIN)
			throw new BadRequestException("Perfil não autorizado.");

		var entity = model.makeEntity();
		entity.setPassword(new BCryptPasswordEncoder().encode(model.getPassword()));
		try {
			entity = service.create(entity);
		} catch (DataIntegrityViolationException ex) {
			throw new EmailViolationExistentException();
		}
		entity.setPassword(null);
		return ResponseEntity.status(201).body(entity);
	}

	@Override
	public ResponseEntity post(@Valid UsuarioDto model) throws DtoToEntityException, NotAuthorizedException, Exception {
		if (model.getPerfil() == Perfil.PACIENTE)
			throw new NotAuthorizedException();

		model.setPassword(new BCryptPasswordEncoder().encode(model.getPassword()));

		return super.post(model);
	}

	@PutMapping("email/{email}/redefinir-senha")
	public ResponseEntity changePassword(Principal principal,
			@NotNull @Valid @Size(max = 255) @RequestBody String password,
			@PathVariable @Valid @Email(message = "E-mail inválido") @NotEmpty(message = "Campo de e-mail necessário") String email)
			throws UsuarioNotFoundException, MethodArgumentNotValidException, HttpMessageNotReadableException,
			UnauthorizationException {

		if (principal.getName() != email)
			throw new UnauthorizationException();

		var oUsuario = service.findByEmail(email);
		if (oUsuario.isEmpty())
			throw new UsuarioNotFoundException();

		var usuario = oUsuario.get();
		usuario.setPassword(new BCryptPasswordEncoder().encode(password));
		service.alter(usuario.getId(), usuario);

		var token = jwtService.generateToken(usuario);

		return ResponseEntity.ok(token);
	}
}
