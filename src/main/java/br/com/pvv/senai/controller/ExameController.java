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
import br.com.pvv.senai.exceptions.PacienteNotFoundException;
import br.com.pvv.senai.model.dto.ExameDto;
import br.com.pvv.senai.service.ExameService;
import br.com.pvv.senai.service.GenericService;
import br.com.pvv.senai.service.PacienteService;
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
	public ResponseEntity post(@Valid ExameDto model) throws DtoToEntityException, Exception {
		var patient = patientService.get(model.getPatientId());
		if (patient == null)
			throw new PacienteNotFoundException(model.getPatientId());

		var entity = model.makeEntity();
		entity.setPaciente(patient);

		entity = getService().create(entity);
		return ResponseEntity.status(201).body(entity);
	}

}
