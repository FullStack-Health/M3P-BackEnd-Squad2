package br.com.pvv.senai.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pvv.senai.controller.filter.ExameFilter;
import br.com.pvv.senai.controller.filter.IFilter;
import br.com.pvv.senai.entity.Exame;
import br.com.pvv.senai.exceptions.DtoToEntityException;
import br.com.pvv.senai.exceptions.ExameNotFoundException;
import br.com.pvv.senai.exceptions.PacienteNotFoundException;
import br.com.pvv.senai.model.dto.ExameDto;
import br.com.pvv.senai.service.ExameService;
import br.com.pvv.senai.service.GenericService;
import br.com.pvv.senai.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/exames")
public class ExameController extends GenericController<ExameDto, Exame> {

	@Autowired
	private ExameService service;

	@Autowired
	private PacienteService patientService;

	@Override
	public GenericService<Exame> getService() {
		return service;
	}

	@Override
	public IFilter<Exame> filterBuilder(Map<String, String> params) throws Exception {
		return new ExameFilter(params);
	}

	@Override
	@Operation(summary = "Cadastro de exames", description = "Realizar o cadastro da entidade (exame) no banco de dados", security = { @SecurityRequirement(name = "bearer-key") })
	public ResponseEntity post(
			@Parameter(description = "Dados referentes a entidade que serão armazenados no banco de dados", required = true) @Valid ExameDto model)
			throws DtoToEntityException, Exception {
		var patient = patientService.get(model.getPatientId());
		if (patient == null)
			throw new PacienteNotFoundException(model.getPatientId());

		var entity = model.makeEntity();
		entity.setPaciente(patient);

		entity = getService().create(entity);
		return ResponseEntity.status(201).body(entity);
	}

	@Override
	@Operation(summary = "Atualização de exames", description = "Realizar a atualização da entidade (exame) no banco de dados", security = { @SecurityRequirement(name = "bearer-key") })
	public ResponseEntity put(
			@Parameter(description = "Identificador da entidade (exame) a ser atualizado.", required = true) Long id,
			@Parameter(description = "Novo conteúdo da entidade a ser atualizado.", required = true) @Valid ExameDto model)
			throws DtoToEntityException, ExameNotFoundException, PacienteNotFoundException {

		System.out.println("ID do exame a ser atualizado: " + id);
		System.out.println("ID do paciente recebido: " + model.getPatientId());

		Exame existingExame = getService().get(id);
		if (existingExame == null) {
			throw new ExameNotFoundException();
		}

		var patient = patientService.get(model.getPatientId());
		if (patient == null)
			throw new PacienteNotFoundException(model.getPatientId());

		var entity = model.makeEntity();
		entity.setPaciente(patient);

		entity = getService().alter(id, entity);
		entity.setPaciente(null);
		return ResponseEntity.ok(entity);
	}

}
