package br.com.pvv.senai.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.pvv.senai.controller.filter.IFilter;
import br.com.pvv.senai.controller.filter.PacienteFilter;
import br.com.pvv.senai.controller.filter.ProntuarioFilter;
import br.com.pvv.senai.entity.Consulta;
import br.com.pvv.senai.entity.Exame;
import br.com.pvv.senai.entity.Paciente;
import br.com.pvv.senai.entity.Usuario;
import br.com.pvv.senai.enums.Perfil;
import br.com.pvv.senai.exceptions.PacienteUserNotFoundException;
import br.com.pvv.senai.model.Prontuario;
import br.com.pvv.senai.model.ProntuarioDetails;
import br.com.pvv.senai.model.dto.PacienteDto;
import br.com.pvv.senai.security.UsuarioService;
import br.com.pvv.senai.service.ConsultaService;
import br.com.pvv.senai.service.ExameService;
import br.com.pvv.senai.service.GenericService;
import br.com.pvv.senai.service.PacienteService;

@Controller
@RestController
@RequestMapping("/pacientes")
public class PacienteController extends GenericController<PacienteDto, Paciente> {

	@Autowired
	private PacienteService service;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ExameService exameService;

	@Autowired
	private ConsultaService consultaService;

	@Override
	public GenericService<Paciente> getService() {
		return service;
	}

	@Override
	public ResponseEntity post(PacienteDto model) throws Exception {
		String cpfLimpo = model.getCPF().replaceAll("[^\\d]", "");
		if (!usuarioService.has(model.getEmail())) {
			Usuario usuario = new Usuario();
			usuario.setPerfil(Perfil.PACIENTE);
			usuario.setEmail(model.getEmail());
			usuario.setPassword( new BCryptPasswordEncoder().encode(cpfLimpo));
			usuario.setNome(model.getName());
			usuario.setTelefone(model.getPhone());
			usuario.setDataNascimento(model.getBirthDate());
			usuario.setCpf(cpfLimpo);
			usuarioService.create(usuario);
		}
		return ResponseEntity.status(201).body(service.create(model.makeEntity()));
	}

	@Override
	public IFilter<Paciente> filterBuilder(Map<String, String> params) {
		return new PacienteFilter(params);
	}

	@GetMapping("prontuarios")
	public List<Prontuario> getProntuario(@RequestParam Map<String, String> params) {
		var filter = new ProntuarioFilter(params);
		var paged = service.paged(filter.example(), filter.getPagination());
		var retorno = paged.map(x -> new Prontuario(x.getId(), x.getName(), x.getInsuranceCompany())).toList();
		return retorno;
	}

	@GetMapping("{id}/prontuarios")
	public ResponseEntity getProntuarioDetails(@PathVariable long id) {
		Paciente paciente = service.get(id);
		if (paciente == null)
			return ResponseEntity.notFound().build();

		var retorno = new ProntuarioDetails();

		retorno.setNome(paciente.getName());
		retorno.setCttDeEmergencia(paciente.getEmergencyContact());
		retorno.setConvenio(paciente.getInsuranceCompany());

		var exames = exameService.findByPacienteId(paciente.getId());
		exames.sort(Comparator.comparing(Exame::getDataExame));
		retorno.setExames(exames);
		var consultas = consultaService.findByPacienteId(paciente.getId());
		consultas.sort(Comparator.comparing(Consulta::getDate));
		retorno.setConsultas(consultas);

		return ResponseEntity.ok(retorno);
	}
}
