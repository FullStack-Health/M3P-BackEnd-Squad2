package br.com.pvv.senai.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import br.com.pvv.senai.security.UsuarioService;
import br.com.pvv.senai.service.GenericService;
import br.com.pvv.senai.utils.SenhaUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends GenericController<UsuarioDto, Usuario> {

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

	@Override
	public ResponseEntity<Page<Usuario>> list(Map<String, String> params) throws Exception {
		var in_list = getService().all();
		var filter = filterBuilder(params);
		var pag = filter.getPagination();
		ArrayList<Usuario> list = new ArrayList<>();
		for (var user : in_list)
			if (user.getPerfil() != Perfil.PACIENTE)
				list.add(user);

		var from = pag.getPageNumber() * pag.getPageSize();
		var to = (1 + pag.getPageNumber()) * pag.getPageSize();
		List<Usuario> sublist;
		if (to < list.size())
			sublist = list.subList(from, to);
		else if (from == 0)
			sublist = list;
		else
			sublist = new ArrayList<>();
		
		PageImpl<Usuario> paged = new PageImpl<>(sublist, pag, list.size());
		
		return ResponseEntity.ok(paged);
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
		entity.setSenhaMascarada(SenhaUtils.gerarSenhaMascarada(model.getPassword()));
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
		var entity = model.makeEntity();
		entity.setPassword(new BCryptPasswordEncoder().encode(model.getPassword()));
		entity.setSenhaMascarada(SenhaUtils.gerarSenhaMascarada(model.getPassword()));
		entity = getService().create(entity);
		return ResponseEntity.status(201).body(entity);
	}

	@PutMapping("email/{email}/redefinir-senha")
	public ResponseEntity changePassword(Principal principal,
			@NotNull @Valid @Size(max = 255) @RequestBody String password,
			@PathVariable @Valid @Email(message = "E-mail inválido") @NotEmpty(message = "Campo de e-mail necessário") String email)
			throws UsuarioNotFoundException, MethodArgumentNotValidException, HttpMessageNotReadableException,
			UnauthorizationException {

//		if (!principal.getName().equals(email))
//			throw new UnauthorizationException();

		var oUsuario = service.findByEmail(email);
		if (oUsuario.isEmpty())
			throw new UsuarioNotFoundException();

		var usuario = oUsuario.get();
		usuario.setPassword(new BCryptPasswordEncoder().encode(password));
		service.alter(usuario.getId(), usuario);

		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Usuario> get(@PathVariable(name = "id") Long id) {
		var usuario = service.get(id);
		if (usuario == null) {
			throw new UsuarioNotFoundException();
		}
		if (usuario.getPerfil() == Perfil.PACIENTE) {
			throw new NotAuthorizedException();
		}
		return ResponseEntity.ok(usuario);
	}

//	@Override
//	public ResponseEntity<Page<Usuario>> list(Map<String, String> params) throws Exception {
//		var pagined = super.list(params);
//		pagined.getBody().getContent().forEach(u -> u.setSenhaMascarada(SenhaUtils.gerarSenhaMascarada(u.getPassword())));
//		return pagined;
//	}

	@Override
	public ResponseEntity<Usuario> put(@PathVariable(name = "id") Long id, @Valid @RequestBody UsuarioDto model) {
		if (getService().get(id) == null) {
			return ResponseEntity.notFound().build();
		}
		if (getService().get(id).getPerfil() == Perfil.PACIENTE) {
			throw new NotAuthorizedException();
		}
		Usuario usuario = model.makeEntity();
		usuario.setSenhaMascarada(SenhaUtils.gerarSenhaMascarada(model.getPassword()));
		usuario.setPassword(new BCryptPasswordEncoder().encode(model.getPassword()));
		Usuario usuarioAtualizado;
		try {
			usuarioAtualizado = getService().alter(id, usuario);
		} catch (DataIntegrityViolationException ex) {
			throw new EmailViolationExistentException();
		}
		return ResponseEntity.ok(usuarioAtualizado);
	}

}
