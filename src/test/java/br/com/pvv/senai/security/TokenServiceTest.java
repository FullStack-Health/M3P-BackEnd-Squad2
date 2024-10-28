package br.com.pvv.senai.security;

import br.com.pvv.senai.entity.Usuario;
import br.com.pvv.senai.enums.Perfil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    TokenService service;

    @Mock
    JwtEncoder encoder;

    @Mock
    JwtDecoder decoder;

    Usuario usuario;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setEmail("usuario@teste.com");
        usuario.setPerfil(Perfil.ADMIN);
    }

    @Test
    @DisplayName("Deve gerar um token para Usuario")
    void generateTokenUsuario() {
        // Given
        Usuario usuario = mock(Usuario.class);
        when(usuario.getEmail()).thenReturn("usuario@teste.com");
        when(usuario.getAuthorities()).thenReturn(null);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("senai-labmedical")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600L))
                .subject(usuario.getEmail())
                .claim("scope", "ROLE_USER")
                .build();

        when(encoder.encode(any(JwtEncoderParameters.class))).thenReturn(mock(Jwt.class));

        // When
        String token = service.generateToken(usuario);
        // Then
        assertNotNull(token);
        assertEquals("mocked-token", token);
        verify(encoder).encode(any(JwtEncoderParameters.class));

    }

    @Test
    @DisplayName("Deve gerar um token recebendo um objeto Authentication")
    void GenerateTokenAuthentication() {
        // Given
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("authUser");
        when(authentication.getAuthorities()).thenReturn(List.of((GrantedAuthority) () -> "ROLE_USER"));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("senai-labmedical")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600L))
                .subject(authentication.getName())
                .claim("scope", "ROLE_USER")
                .build();

        when(encoder.encode(any(JwtEncoderParameters.class))).thenReturn(mock(Jwt.class));

        // When
        String token = service.generateToken(authentication);

        // Then
        assertNotNull(token);
        assertEquals("mocked-token", token);
        verify(encoder).encode(any(JwtEncoderParameters.class));
    }

    @Test
    @DisplayName("Deve validar um token")
    void validateToken() {
        // Given
        String token = "token-válido";
        when(decoder.decode(token)).thenReturn(mock(Jwt.class));

        // When
        String subject = service.validateToken(token);

        // Then
        assertNotNull(subject);
        assertEquals("subject", subject);
        verify(decoder).decode(token);
    }
}