package br.com.pvv.senai.controller;

import br.com.pvv.senai.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@GetMapping
	public ResponseEntity get(Authentication authentication) {
		var dashboard = dashboardService.get(authentication);
		return ResponseEntity.ok(dashboard);
	}

}
