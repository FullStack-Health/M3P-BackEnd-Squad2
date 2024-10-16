package br.com.pvv.senai.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pvv.senai.controller.filter.IFilter;
import br.com.pvv.senai.entity.Consulta;
import br.com.pvv.senai.exceptions.DtoToEntityException;
import br.com.pvv.senai.exceptions.NotRequiredByProjectException;
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

	@Override
	public GenericService<Consulta> getService() {
		return service;
	}

	@Override
	public IFilter<Consulta> filterBuilder(Map<String, String> params) throws NotRequiredByProjectException {
		throw new NotRequiredByProjectException();
	}

}
