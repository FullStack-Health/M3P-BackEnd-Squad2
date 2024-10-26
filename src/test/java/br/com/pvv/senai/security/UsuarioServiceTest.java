package br.com.pvv.senai.security;

import br.com.pvv.senai.entity.Usuario;
import br.com.pvv.senai.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsuarioServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UsuarioService usuarioService;

    Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("usuario@teste.com");
    }

    @Test
    @DisplayName("Deve injetar o repositório")
    void getRepository() {
        assertNotNull(usuarioService.getRepository());
    }

    @Test
    @DisplayName("Deve retornar true caso pessoa usuária exista, a partir do e-mail")
    void has() {
        // Given
        String email = "usuario@teste.com";
        when(userRepository.exists(any(Example.class))).thenReturn(true);
        // When
        Boolean exists = usuarioService.has(email);
        // Then
        assertNotNull(exists);
        assertTrue(exists);
        verify(userRepository).exists(any());
    }

    @Test
    @DisplayName("Deve retornar lista de pessoas usuárias")
    void list() {
        // Given
        when(userRepository.findAll()).thenReturn(List.of(usuario));
        // When
        var resultado = usuarioService.all();
        // Then
        assertNotNull(resultado);
        assertEquals(usuario.getId(), resultado.get(0).getId());
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar página de pessoas usuárias")
    void paged() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Usuario> page = new PageImpl<>(List.of(usuario), pageable, 1);
        when(userRepository.findAll(any(Example.class), any(Pageable.class))).thenReturn(page);
        // When
        Page<Usuario> resultado = usuarioService.paged(Example.of(usuario), pageable);
        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals(usuario.getId(), resultado.getContent().get(0).getId());
        verify(userRepository).findAll(any(Example.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Deve criar pessoa usuária")
    void create() {
        // Given
        when(userRepository.save(any(Usuario.class))).thenReturn(usuario);
        // When
        Usuario usuarioSalvo = usuarioService.create(usuario);
        // Then
        assertNotNull(usuarioSalvo);
        assertEquals(usuario.getId(), usuarioSalvo.getId());
        verify(userRepository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve retornar pessoa usuária por id")
    void get() {
        // Given
        when(userRepository.findById(any())).thenReturn(Optional.of(usuario));
        // When
        Usuario usuarioEncontrado = usuarioService.get(1L);
        // Then
        assertNotNull(usuarioEncontrado);
        assertEquals(usuario.getId(), usuarioEncontrado.getId());
        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve alterar pessoa usuária")
    void alter() {
        // Given
        when(userRepository.save(any(Usuario.class))).thenReturn(usuario);
        // When
        Usuario usuarioAlterado = usuarioService.alter(1L, usuario);
        // Then
        assertNotNull(usuarioAlterado);
        assertEquals(usuario.getId(), usuarioAlterado.getId());
        verify(userRepository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve retornar true ao deletar pessoa usuária")
    void delete() {
        // Given
        when(userRepository.findById(any())).thenReturn(Optional.of(usuario));
        // When
        boolean deletado = usuarioService.delete(1L);
        // Then
        assertTrue(deletado);
        verify(userRepository).delete(usuario);
    }

    @Test
    @DisplayName("Deve retornar quantidade de pessoas usuárias no repositório")
    void count(){
        // Given
        when(userRepository.count()).thenReturn(1L);
        // When
        Long quantidade = usuarioService.count();
        // Then
        assertEquals(1L, quantidade);
        verify(userRepository).count();
    }


}