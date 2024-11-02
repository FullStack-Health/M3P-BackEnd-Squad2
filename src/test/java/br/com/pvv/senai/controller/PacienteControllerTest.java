package br.com.pvv.senai.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import br.com.pvv.senai.entity.Consulta;
import br.com.pvv.senai.entity.Endereco;
import br.com.pvv.senai.entity.Paciente;
import br.com.pvv.senai.entity.Usuario;
import br.com.pvv.senai.enums.Perfil;
import br.com.pvv.senai.model.dto.ConsultaDto;
import br.com.pvv.senai.model.dto.PacienteDto;
import br.com.pvv.senai.security.SecurityFilter;
import br.com.pvv.senai.security.UsuarioService;
import br.com.pvv.senai.service.ConsultaService;
import br.com.pvv.senai.service.ExameService;
import br.com.pvv.senai.service.PacienteService;

@AutoConfigureMockMvc
@WebMvcTest(PacienteController.class)
@ExtendWith(MockitoExtension.class)
public class PacienteControllerTest {

	@InjectMocks
	PacienteController controller;

	@MockBean
	PacienteService service;

	@MockBean
	UsuarioService userService;

	@MockBean
	ExameService exameService;

	@MockBean
	ConsultaService consultaService;

	@MockBean
	SecurityFilter securityFilter;

	@Autowired
	MockMvc mvc;

	Usuario usuarioCorrect;
	Usuario usuarioWrongMail;
	Usuario usuarioPaciente;
	PacienteDto putPaciente;
	Paciente paciente;

	ConsultaDto consultaDto;
	ConsultaDto putConsulta;
	Consulta consulta;

	String token;

	void clearEntitys() {
		usuarioCorrect = new Usuario();
		usuarioCorrect.setNome("Nome de usuário");
		usuarioCorrect.setEmail("usuario@teste.com");
		usuarioCorrect.setDataNascimento(java.sql.Date.valueOf(LocalDate.of(1980, 10, 10)));
		usuarioCorrect.setTelefone("(48) 9 9999-9999");
		usuarioCorrect.setCpf("000.000.000-00");
		usuarioCorrect.setPassword("12341234");
		usuarioCorrect.setPerfil(Perfil.MEDICO);

		usuarioWrongMail = new Usuario();
		usuarioWrongMail.setNome("Nome de usuário");
		usuarioWrongMail.setEmail("usuarioteste.com");
		usuarioWrongMail.setDataNascimento(java.sql.Date.valueOf(LocalDate.of(1980, 10, 10)));
		usuarioWrongMail.setTelefone("(48) 9 9999-9999");
		usuarioWrongMail.setCpf("000.000.000-00");
		usuarioWrongMail.setPassword("12341234");
		usuarioWrongMail.setPerfil(Perfil.MEDICO);

		usuarioPaciente = new Usuario();
		usuarioPaciente.setNome("Nome de usuário");
		usuarioPaciente.setEmail("carlos.souza@example.com");
		usuarioPaciente.setDataNascimento(java.sql.Date.valueOf(LocalDate.of(1980, 10, 10)));
		usuarioPaciente.setTelefone("(48) 9 9999-9999");
		usuarioPaciente.setCpf("000.000.000-00");
		usuarioPaciente.setPassword("12341234");
		usuarioPaciente.setPerfil(Perfil.PACIENTE);

		consulta = new Consulta();

		consulta.setReason("Check-up anual");
		consulta.setDate(LocalDate.now());
		consulta.setTime(LocalTime.now());
		consulta.setIssueDescription("Paciente relatou dores de cabeça frequentes e cansaço.");
		consulta.setPrescribedMedication("Paracetamol 500mg");
		consulta.setObservation("Recomendada hidratação e repouso.");

		putConsulta = new ConsultaDto();

		putConsulta.setReason("Check-up anual");
		putConsulta.setDate(LocalDate.now());
		putConsulta.setTime(LocalTime.now());
		putConsulta.setIssueDescription("Paciente relatou dores de cabeça frequentes e cansaço.");
		putConsulta.setPrescribedMedication("Paracetamol 500mg");
		putConsulta.setObservation("Recomendada hidratação e repouso.");

		consultaDto = new ConsultaDto();

		consultaDto.setReason("Consulta de rotina");
		consultaDto.setDate(LocalDate.of(2024, 10, 31));
		consultaDto.setTime(LocalTime.of(14, 30, 0));
		consultaDto.setIssueDescription("Paciente relata dores intensas na região lombar.");
		consultaDto.setPrescribedMedication("Ibuprofeno 400mg");
		consultaDto.setObservation("Paciente deve retornar em 2 semanas para acompanhamento.");

		PacienteDto pacienteDto = new PacienteDto();
		pacienteDto.setId(67890);
		pacienteDto.setName("Ana Pereira");
		consultaDto.setPatient(pacienteDto);

		consultaDto.setPatientId(67890);

		paciente = new Paciente();

		paciente.setName("Carlos Souza");
		paciente.setGender("Masculino");
		paciente.setBirthDate(new Date(85, 3, 25));
		paciente.setCPF("987.654.321-00");
		paciente.setRG("SP-43.210.987");
		paciente.setMaritalStatus("Casado");
		paciente.setPhone("(11) 9 8765-4321");
		paciente.setEmail("carlos.souza@example.com");
		paciente.setBirthCity("São Paulo");
		paciente.setEmergencyContact("(11) 9 8765-4321");
		paciente.setAllergies("Nenhuma");
		paciente.setSpecialCare("Nenhum");
		paciente.setInsuranceCompany("Bradesco Saúde");
		paciente.setInsuranceNumber("9876543210");
		paciente.setInsuranceExpiration(new Date(126, 5, 30));

		putPaciente = new PacienteDto();

		putPaciente.setName("Carlos Souza");
		putPaciente.setGender("Masculino");
		putPaciente.setBirthDate(new Date(85, 3, 25));
		putPaciente.setCPF("987.654.321-00");
		putPaciente.setRG("SP-43.210.987");
		putPaciente.setMaritalStatus("Casado");
		putPaciente.setPhone("(11) 9 8765-4321");
		putPaciente.setEmail("carlos.souza@example.com");
		putPaciente.setBirthCity("São Paulo");
		putPaciente.setEmergencyContact("(11) 9 8765-4321");
		putPaciente.setAllergies("Nenhuma");
		putPaciente.setSpecialCare("Nenhum");
		putPaciente.setInsuranceCompany("Bradesco Saúde");
		putPaciente.setInsuranceNumber("9876543210");
		putPaciente.setInsuranceExpiration(new Date(126, 5, 30));

		Endereco endereco = new Endereco();

		endereco.setCEP("38400-000");
		endereco.setCidade("Uberlândia");
		endereco.setLogradouro("Rua Exemplo");
		endereco.setNumero(123);
		endereco.setComplemento("Apto 101");
		endereco.setBairro("Centro");
		endereco.setPontoDeReferencia("Próximo ao supermercado");

		paciente.setUsuario(usuarioPaciente);
		paciente.setAddress(endereco);

		consulta.setPatient(paciente);
	}

	@BeforeEach
	void setup() {
		clearEntitys();
	};

	@Test
	@DisplayName("LISTAR PACIENTES - 200 - OBTÉM LISTA DOS PACIENTES")
	void list_200() throws Exception {
		when(service.all()).thenReturn(List.of(paciente));

		var resp = controller.list(Map.of());
		var body = resp.getBody().getContent().get(0);

		verify(service).all();

		assertNotNull(resp);
		assertEquals(paciente.getName(), body.getName());
	}

	@Test
	@DisplayName("LISTAR PACIENTES - 200 - OBTÉM LISTA DOS PACIENTES COM FILTRO")
	void list_200_withFilter() throws Exception {
		when(service.paged(any(), any())).thenReturn(new PageImpl(List.of(paciente)));

		var resp = controller.list(Map.of("name", paciente.getName()));
		var body = resp.getBody().getContent().get(0);

		verify(service).paged(any(), any());

		assertNotNull(resp);
		assertEquals(paciente.getName(), body.getName());
	}

	@Test
	@DisplayName("CONSULTA PACIENTE - 200 - OBTÉM PACIENTE DETERMINADO")
	void get_200() throws Exception {
		when(service.get(anyLong())).thenReturn(paciente);

		var resp = controller.get(1L);
		var body = (Paciente) resp.getBody();

		verify(service).get(anyLong());

		assertNotNull(resp);
		assertEquals(paciente.getName(), body.getName());
	}

	@Test
	@DisplayName("CONSULTA PACIENTE - 200 - OBTÉM PACIENTE DETERMINADO")
	void put_200() throws Exception {
		when(service.get(anyLong())).thenReturn(paciente);
		when(service.alter(anyLong(), any())).thenReturn(paciente);

		var resp = controller.put(1L, putPaciente);
		var body = (Paciente) resp.getBody();

		verify(service).get(anyLong());
		verify(service).alter(anyLong(), any());

		assertNotNull(resp);
		assertNull(resp.getBody());
	}

	@Test
	@DisplayName("EXCLUI PACIENTE - 200 - REMOVE PACIENTE DETERMINADO")
	void delete_200() throws Exception {
		when(service.delete(anyLong())).thenReturn(true);

		controller.delete(1L);
		
		verify(service).delete(anyLong());
	}

}
