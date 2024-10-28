package br.com.pvv.senai.controller;

import java.util.Map;

import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pvv.senai.controller.filter.IFilter;
import br.com.pvv.senai.entity.Consulta;
import br.com.pvv.senai.entity.Paciente;
import br.com.pvv.senai.exceptions.DtoToEntityException;
import br.com.pvv.senai.exceptions.NotRequiredByProjectException;
import br.com.pvv.senai.exceptions.PacienteNotFoundException;
import br.com.pvv.senai.model.dto.ConsultaDto;
import br.com.pvv.senai.service.ConsultaService;
import br.com.pvv.senai.service.GenericService;
import br.com.pvv.senai.service.PacienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/consultas")
public class ConsultaController extends GenericController<ConsultaDto, Consulta> {

	@Autowired
	private ConsultaService service;

	@Autowired
	private PacienteService patientService;

	@Override
	public GenericService<Consulta> getService() {
		return service;
	}

	@Override
	public IFilter<Consulta> filterBuilder(Map<String, String> params) throws NotRequiredByProjectException {
		throw new NotRequiredByProjectException();
	}

	@Override
	public ResponseEntity post(@Valid ConsultaDto model) throws DtoToEntityException, Exception {
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
	public ResponseEntity get(Long id) {
		var retorno = getService().get(id);
		if (retorno == null)
			return ResponseEntity.notFound().build();
		retorno.setPatient(null);
		return ResponseEntity.ok(retorno);
	}

	@Override
	public ResponseEntity put(Long id, @Valid ConsultaDto model)
			throws DtoToEntityException, PacienteNotFoundException {
		if (getService().get(id) == null)
			return ResponseEntity.notFound().build();

		Paciente patient = patientService.get(model.getPatientId());
		if (patient == null)
			throw new PacienteNotFoundException(model.getPatientId());

		patient = (Paciente) ((HibernateProxy) patient).getHibernateLazyInitializer().getImplementation();

		var entity = model.makeEntity();
		entity.setPatient(patient);

		entity = getService().alter(id, entity);
		entity.setPatient(patient);
		return ResponseEntity.ok(entity);
	}

}
