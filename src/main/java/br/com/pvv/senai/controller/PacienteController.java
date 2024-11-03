package br.com.pvv.senai.controller;

import java.util.Comparator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import br.com.pvv.senai.model.dto.PacienteDto;
import br.com.pvv.senai.model.dto.ProntuarioDto;
import br.com.pvv.senai.security.UsuarioService;
import br.com.pvv.senai.service.ConsultaService;
import br.com.pvv.senai.service.ExameService;
import br.com.pvv.senai.service.GenericService;
import br.com.pvv.senai.service.PacienteService;
import br.com.pvv.senai.utils.SenhaUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

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
	@Operation(summary = "Cadastrar paciente", description = "Realiza o cadastro de um novo paciente (caso não possua usuário, um usuário será cadastrado para ele).", security = {
			@SecurityRequirement(name = "bearer-key") })
	public ResponseEntity post(
			@Parameter(description = "Dados do paciente a ser cadastrado.") @RequestBody @Valid PacienteDto model)
			throws Exception {
		String cpfLimpo = model.getCPF().replaceAll("[^\\d]", "");
		if (!usuarioService.has(model.getEmail())) {
			Usuario usuario = new Usuario();
			usuario.setPerfil(Perfil.PACIENTE);
			usuario.setEmail(model.getEmail());
			usuario.setPassword(new BCryptPasswordEncoder().encode(cpfLimpo));
			usuario.setSenhaMascarada(SenhaUtils.gerarSenhaMascarada(cpfLimpo));
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

//	@GetMapping("prontuarios")
//	public List<Prontuario> getProntuario(@RequestParam Map<String, String> params) {
//		var filter = new ProntuarioFilter(params);
//		var paged = service.paged(filter.example(), filter.getPagination());
//		var retorno = paged.map(x -> new Prontuario(x.getId(), x.getName(), x.getInsuranceCompany())).toList();
//		return retorno;
//	}

	@GetMapping("prontuarios")
	@Operation(summary = "Consulta prontuários", description = "Obtém os dados do prontuário dos pacientes.", security = { @SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<Page<ProntuarioDto>> list(
			@Parameter(description = "Nome do paciente a ser consultado.")
			@RequestParam(required = false) String nome,
			@Parameter(description = "Identificador do paciente a ser consultado.")
			@RequestParam(required = false) Long id,
			@RequestParam Map<String, String> params) {
		var filter = new ProntuarioFilter(params);
		var paged = service.paged(filter.example(), filter.getPagination());
		Page<ProntuarioDto> retorno = paged.map(paciente -> new ProntuarioDto(paciente,
				exameService.findByPacienteId(paciente.getId()), consultaService.findByPacienteId(paciente.getId())));

		if (retorno.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(retorno);

	}

	@GetMapping("{id}/prontuarios")
	@Operation(summary = "Detalhes do prontuário", description = "Obtém detalhes do prontuário de determinado paciente.", security = { @SecurityRequirement(name = "bearer-key") })
	public ResponseEntity getProntuarioDetails(
			@Parameter(description = "Identificador do paciente a ter o prontuário consultado")
			@PathVariable long id
			) {
		Paciente paciente = service.get(id);
		if (paciente == null)
			return ResponseEntity.notFound().build();

//		var retorno = new ProntuarioDetails();

//		retorno.setNome(paciente.getName());
//		retorno.setCttDeEmergencia(paciente.getEmergencyContact());
//		retorno.setConvenio(paciente.getInsuranceCompany());
//
		var exames = exameService.findByPacienteId(paciente.getId());
		exames.sort(Comparator.comparing(Exame::getDataExame));

		var consultas = consultaService.findByPacienteId(paciente.getId());
		consultas.sort(Comparator.comparing(Consulta::getDate));

		ProntuarioDto retorno = new ProntuarioDto(paciente, exames, consultas);
		retorno.setExames(exames);
		retorno.setConsultas(consultas);

		return ResponseEntity.ok(retorno);
	}
}
