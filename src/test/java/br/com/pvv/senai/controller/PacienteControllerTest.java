package br.com.pvv.senai.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.pvv.senai.controller.filter.UsuarioFilter;
import br.com.pvv.senai.entity.Consulta;
import br.com.pvv.senai.entity.Endereco;
import br.com.pvv.senai.entity.Exame;
import br.com.pvv.senai.entity.Paciente;
import br.com.pvv.senai.entity.Usuario;
import br.com.pvv.senai.enums.Perfil;
import br.com.pvv.senai.model.dto.EnderecoDto;
import br.com.pvv.senai.service.PacienteService;

@WebMvcTest(controllers = PacienteController.class)
@AutoConfigureMockMvc
@ActiveProfiles("Test")
public class PacienteControllerTest {
	@Autowired
	MockMvc mvc;

	@MockBean
	PacienteService service;

	@MockBean
	static AuthenticationManager authenticationManager;

	Paciente paciente;

	@BeforeAll
	static void configAuth() {
		UserDetails userDetails = User.withUsername("user").password("password").roles("ADMIN").build();

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@BeforeEach
	public void setup() {
		long id = 1L;
		String name = "João Silva";
		String gender = "Masculino";
		Date birthDate = new Date(90, 5, 15); // 15 de junho de 1990
		String cPF = "123.456.789-00";
		String rG = "MG-12.345.678";
		String maritalStatus = "Solteiro";
		String phone = "(31) 98765-4321";
		String email = "joao.silva@example.com";
		String birthCity = "Belo Horizonte";
		String emergencyContact = "Maria Silva - (31) 91234-5678";
		String allergies = "Nenhuma";
		String specialCare = "Nenhum";
		String insuranceCompany = "Saúde Total";
		String insuranceNumber = "9876543210";
		Date insuranceExpiration = new Date(125, 11, 31); // 31 de dezembro de 2025
		Endereco address = new Endereco(1L, "30123-456", "Belo Horizonte", "Rua das Flores", 123, "Apto 101", "Centro",
				"Próximo ao parque");

		Usuario usuario = new Usuario(1L, "João Silva", "(31) 98765-4321", "joao.silva@example.com",
				new Date(90, 5, 15), "123.456.789-00", "senha123", "senhaMascarada123", Perfil.ADMIN);
		List<Consulta> consultas = new ArrayList<>();
		List<Exame> exames = new ArrayList<>();

		// Instanciação do objeto Paciente
		paciente = new Paciente(id, name, gender, birthDate, cPF, rG, maritalStatus, phone, email, birthCity,
				emergencyContact, allergies, specialCare, insuranceCompany, insuranceNumber, insuranceExpiration,
				address, usuario, consultas, exames);
	}

	@Test
	void listarPacinetes() throws Exception {
		// itens
		
		
		// quando ...
		when(service.paged(any(), any())).thenReturn(new PageImpl<Paciente>(List.of(paciente)));
		
		// executa;
		Map<String, String> params = new HashMap<String, String>();
		
		StringBuilder url = new StringBuilder("/pacientes?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
		mvc.perform(get(url.toString())).andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].name").value(paciente.getName()));

		verify(service).paged(any(), any());
	}

}
