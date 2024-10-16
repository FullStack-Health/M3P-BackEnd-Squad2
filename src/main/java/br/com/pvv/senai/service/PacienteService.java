package br.com.pvv.senai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.pvv.senai.entity.Paciente;
import br.com.pvv.senai.repository.PacienteRepository;
import br.com.pvv.senai.security.UsuarioService;

@Service
public class PacienteService extends GenericService<Paciente> {

	@Autowired
	private PacienteRepository repository;

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private UsuarioService userService;

	@Override
	public JpaRepository<Paciente, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Paciente create(Paciente model) {
		var endereco = this.enderecoService.create(model.getEndereco());
		var usuario = this.userService.findByEmail(model.getEmail()).get();
		model.setUsuario(usuario);
		var retorno = super.create(model);
//		usuario.setPassword(null); -> applied @JsonIgnore
		return retorno;
	}

	@Override
	public Paciente alter(long id, Paciente model) {
		model.setId(id);
		var endereco = model.getEndereco();
		if (endereco != null && endereco.getId() == 0)
			endereco = this.enderecoService.create(model.getEndereco());
		var usuario = this.userService.findByEmail(model.getEmail()).get();
		model.setUsuario(usuario);
		var retorno = super.create(model);
//		usuario.setPassword(null); -> applied @JsonIgnore
		return retorno;
	}

	public Paciente findByEmail(String email) {
		var paciente = repository.findByEmail(email);
		return paciente;
	}

	public long count() {
		return repository.count();
	}

}
