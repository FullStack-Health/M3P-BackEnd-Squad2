package br.com.pvv.senai.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pvv.senai.controller.filter.ConsultaFilter;
import br.com.pvv.senai.controller.filter.IFilter;
import br.com.pvv.senai.entity.Consulta;
import br.com.pvv.senai.entity.Paciente;
import br.com.pvv.senai.exceptions.ConsultaNotFoundException;
import br.com.pvv.senai.exceptions.DtoToEntityException;
import br.com.pvv.senai.exceptions.NotRequiredByProjectException;
import br.com.pvv.senai.exceptions.PacienteNotFoundException;
import br.com.pvv.senai.model.dto.ConsultaDto;
import br.com.pvv.senai.service.ConsultaService;
import br.com.pvv.senai.service.GenericService;
import br.com.pvv.senai.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/consultas")
public class ConsultaController extends GenericController<ConsultaDto, Consulta> {

	@Autowired
	ConsultaService service;

	@Autowired
	private PacienteService patientService;

	@Override
	public GenericService<Consulta> getService() {
		return service;
	}

	@Override
	public IFilter<Consulta> filterBuilder(Map<String, String> params) throws NotRequiredByProjectException {
		return new ConsultaFilter(params);
	}

	@Override
	@Operation(summary = "Cadastrar consulta", description = "Realiza o cadastro da entidade consulta.", security = {
			@SecurityRequirement(name = "bearer-key") })
	public ResponseEntity post(@Parameter(description = "Dados da consulta a ser cadastrada.") @Valid ConsultaDto model)
			throws DtoToEntityException, Exception {
		var id = model.getPatientId();
		var patient = patientService.get(id);
		if (patient == null)
			throw new PacienteNotFoundException(id);
		var entity = model.makeEntity();

		entity.setPatient(patient);
		entity = getService().create(entity);
		return ResponseEntity.status(201).body(entity);
	}

	@Override
	@Operation(summary = "Consultar consulta", description = "Realiza a consulta de determinada consulta", security = {
			@SecurityRequirement(name = "bearer-key") })
	public ResponseEntity get(@Parameter(description = "Identificador da consulta a ser consultada") Long id) {
		var retorno = getService().get(id);
		if (retorno == null)
			return ResponseEntity.notFound().build();
		retorno.setPatient(null);
		return ResponseEntity.ok(retorno);
	}

	@Override
	@Operation(summary = "Atualiza consulta", description = "Realiza a atualização de determinada consulta", security = {
			@SecurityRequirement(name = "bearer-key") })
	public ResponseEntity put(@Parameter(description = "Identificador da consulta a ser atualizada") Long id,
			@Parameter(description = "Dados da consulta a serem atualizados") @Valid ConsultaDto model)
			throws DtoToEntityException, PacienteNotFoundException {
		if (getService().get(id) == null)
			return ResponseEntity.notFound().build();

		Paciente patient = patientService.get(model.getPatientId());
		if (patient == null)
			throw new PacienteNotFoundException(model.getPatientId());

		var entity = model.makeEntity();
		entity.setPatient(patient);

		entity = getService().alter(id, entity);
		entity.setPatient(patient);
		return ResponseEntity.ok(entity);
	}

}
