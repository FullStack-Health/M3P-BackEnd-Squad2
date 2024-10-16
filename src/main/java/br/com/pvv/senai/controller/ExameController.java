package br.com.pvv.senai.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pvv.senai.controller.filter.IFilter;
import br.com.pvv.senai.entity.Exame;
import br.com.pvv.senai.exceptions.NotRequiredByProjectException;
import br.com.pvv.senai.model.dto.ExameDto;
import br.com.pvv.senai.service.ExameService;
import br.com.pvv.senai.service.GenericService;

@RestController
@RequestMapping("/exames")
public class ExameController extends GenericController<ExameDto, Exame> {

	@Autowired
	private ExameService service;

	@Override
	public GenericService<Exame> getService() {
		return service;
	}

	@Override
	public IFilter<Exame> filterBuilder(Map<String, String> params) throws Exception {
		throw new NotRequiredByProjectException();
	}

}
