package br.com.pvv.senai.controller;

import br.com.pvv.senai.entity.Usuario;
import br.com.pvv.senai.enums.Perfil;
import br.com.pvv.senai.security.SecurityFilter;
import br.com.pvv.senai.security.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    UsuarioService usuarioService;

    @MockBean
    private SecurityFilter secFilter;

    Usuario usuario;

    @BeforeEach
    void setup(){
        usuario = new Usuario();
        usuario.setNome("Nome de usuário");
        usuario.setCpf("000.000.000-00");
        usuario.setDataNascimento(java.sql.Date.valueOf(LocalDate.of(1980, 10, 10)));
        usuario.setEmail("usuario@teste.com");
        usuario.setPerfil(Perfil.MEDICO);
    };

    @Test
    void getService() {
    }

    @Test
    void filterBuilder() {
    }

    @Test
    void list() {
    }

    @Test
    void me() {
    }

    @Test
    void register() {
    }

    @Test
    @DisplayName("Deve cadastrar uma pessoa usuária")
    void post() throws Exception {
        when(usuarioService.create(any(Usuario.class))).thenReturn(usuario);

        mvc.perform(MockMvcRequestBuilders.post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "nome": "Nome da pessoa usuária",
                        "email": "usuario@teste.com",
                        "dataNascimento": "1980-10-10",
                        "telefone": "(48) 99999-9999",
                        "cpf": "000.000.000-00",
                        "password": "12341234",
                        "perfil": "MEDICO"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Nome da pessoa usuária"));
    }

    @Test
    void changePassword() {
    }

    @Test
    void get() {
    }

    @Test
    void put() {
    }

    @Test
    void delete() {
    }
}
