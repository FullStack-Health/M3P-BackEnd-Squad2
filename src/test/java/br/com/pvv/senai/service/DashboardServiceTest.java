package br.com.pvv.senai.service;

import br.com.pvv.senai.model.DashboardResponse;
import br.com.pvv.senai.security.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DashboardServiceTest {

    @InjectMocks
    DashboardService service;

    @Mock
    ConsultaService consultaService;

    @Mock
    ExameService exameService;

    @Mock
    PacienteService pacienteService;

    @Mock
    UsuarioService usuarioService;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar informações sobre o sistema para pessoas usuárias do tipo ADMIN")
    void getPerfilAdmin(){
        // Given
        when(consultaService.count()).thenReturn(1L);
        when(exameService.count()).thenReturn(2L);
        when(pacienteService.count()).thenReturn(3L);
        when(usuarioService.count()).thenReturn(5L);
        when(authentication.getAuthorities()).thenReturn((Collection) List.of(new SimpleGrantedAuthority("SCOPE_ROLE_ADMIN")));
        // When
        DashboardResponse response = service.get(authentication);
        // Then
        assertNotNull(response);
        assertEquals(response.getQtdConsultas(), 1L);
        assertEquals(response.getQtdExames(), 2L);
        assertEquals(response.getQtdPacientes(), 3L);
        assertEquals(response.getQtdUsuarios(), 5L);
        verify(consultaService).count();
        verify(exameService).count();
        verify(pacienteService).count();
        verify(usuarioService).count();
    }

    @Test
    @DisplayName("Deve retornar informações sobre o sistema para pessoas usuárias do tipo MEDICO")
    void getPerfilMedico() {
        // Given
        when(consultaService.count()).thenReturn(1L);
        when(exameService.count()).thenReturn(2L);
        when(pacienteService.count()).thenReturn(3L);
        when(authentication.getAuthorities()).thenReturn((Collection) List.of(new SimpleGrantedAuthority("SCOPE_ROLE_MEDICO")));
        // When
        DashboardResponse response = service.get(authentication);
        // Then
        assertNotNull(response);
        assertEquals(1L, response.getQtdConsultas());
        assertEquals(2L, response.getQtdExames());
        assertEquals(3L, response.getQtdPacientes());
        assertNull(response.getQtdUsuarios());
        verify(consultaService).count();
        verify(exameService).count();
        verify(pacienteService).count();
        verify(usuarioService, never()).count();
    }



}