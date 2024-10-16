package br.com.pvv.senai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pvv.senai.model.DashboardResponse;
import br.com.pvv.senai.service.ConsultaService;
import br.com.pvv.senai.service.ExameService;
import br.com.pvv.senai.service.PacienteService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private ConsultaService consultaService;
	@Autowired
	private ExameService exameService;
	@Autowired
	private PacienteService pacienteService;

	@GetMapping
	public ResponseEntity get() {
		var dashboard = new DashboardResponse();
		dashboard.setQtdConsultas(consultaService.count());
		dashboard.setQtdExames(exameService.count());
		dashboard.setQtdPacientes(pacienteService.count());
		return ResponseEntity.ok(dashboard);
	}

}
