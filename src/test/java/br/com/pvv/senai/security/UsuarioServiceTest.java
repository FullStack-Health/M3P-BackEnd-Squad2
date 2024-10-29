package br.com.pvv.senai.security;

<<<<<<< Updated upstream
import br.com.pvv.senai.entity.Usuario;
import br.com.pvv.senai.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
=======
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

>>>>>>> Stashed changes
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

<<<<<<< Updated upstream
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
=======
import br.com.pvv.senai.entity.Usuario;
import br.com.pvv.senai.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
>>>>>>> Stashed changes

class UsuarioServiceTest {

	@Mock
	UserRepository userRepository;

<<<<<<< Updated upstream
    @Mock
    EntityManager em;

    @InjectMocks
    UsuarioService usuarioService;
=======
	@Mock
	EntityManager em;
>>>>>>> Stashed changes

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

<<<<<<< Updated upstream
    @Test
    @DisplayName("Deve retornar página de pessoas usuárias")
    void paged() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<Usuario> criteriaQuery = mock(CriteriaQuery.class);
        Root<Usuario> root = mock(Root.class);
        TypedQuery<Usuario> query = mock(TypedQuery.class);

        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Usuario.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Usuario.class)).thenReturn(root);
        when(em.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(usuario));

        // When
        Page<Usuario> resultado = usuarioService.paged(Example.of(usuario), pageable);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals(usuario.getId(), resultado.getContent().get(0).getId());
        verify(em).createQuery(any(CriteriaQuery.class));
    }
=======
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
>>>>>>> Stashed changes

	@Test
	@DisplayName("Deve retornar página de pessoas usuárias")
	void paged() {
		// Given
		// Given
		CriteriaBuilder cb = mock(CriteriaBuilder.class);
		CriteriaQuery<Usuario> fakeCriteriaQuery = mock(CriteriaQuery.class);
		Root<Usuario> fakeRoot = mock(Root.class);
		TypedQuery<Usuario> fTypedQuery = mock(TypedQuery.class);
		Pageable pageable = PageRequest.of(0, 10);
		Page<Usuario> page = new PageImpl<>(List.of(usuario), pageable, 1);

		when(em.getCriteriaBuilder()).thenReturn(cb);
		when(em.createQuery(fakeCriteriaQuery)).thenReturn(fTypedQuery);
//		when(userRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
		when(cb.createQuery(Usuario.class)).thenReturn(fakeCriteriaQuery);
		when(fakeCriteriaQuery.from(Usuario.class)).thenReturn(fakeRoot);
		when(fTypedQuery.getResultList()).thenReturn(List.of(usuario));
		when(fakeCriteriaQuery.where(any(Expression.class))).thenReturn(fakeCriteriaQuery);
		when(fakeCriteriaQuery.select(any(Expression.class))).thenReturn(fakeCriteriaQuery);
		when(userRepository.findAll(any(Example.class), any(Pageable.class))).thenReturn(page);

		// when
		Page<Usuario> resultado = usuarioService.paged(Example.of(usuario), pageable);

		// Then
		assertNotNull(resultado);
		assertEquals(1, resultado.getTotalElements());
		assertEquals(usuario.getId(), resultado.getContent().get(0).getId());
//		verify(userRepository).findAll(any(Example.class), any(Pageable.class));
		verify(em).getCriteriaBuilder();
		verify(em).createQuery(fakeCriteriaQuery);
		verify(cb).createQuery(Usuario.class);
		verify(fakeCriteriaQuery).from(Usuario.class);
		verify(fakeCriteriaQuery).select(any(Expression.class));
		verify(fTypedQuery).getResultList();
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
		CriteriaBuilder mockCriteriaBuilder = mock(CriteriaBuilder.class);
//		when(em.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

		// Mock do CriteriaQuery
		CriteriaQuery<Usuario> fakeCriteriaQuery = mock(CriteriaQuery.class);
		when(mockCriteriaBuilder.createQuery(Usuario.class)).thenReturn(fakeCriteriaQuery);

		// When
		Usuario usuarioEncontrado = usuarioService.get(1L);
		// Then
		assertNotNull(usuarioEncontrado);
		assertEquals(usuario.getId(), usuarioEncontrado.getId());
//		verify(em).getCriteriaBuilder();
//		verify(mockCriteriaBuilder).createQuery(Usuario.class);
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
	void count() {
		// Given
		when(userRepository.count()).thenReturn(1L);
		// When
		Long quantidade = usuarioService.count();
		// Then
		assertEquals(1L, quantidade);
		verify(userRepository).count();
	}

}