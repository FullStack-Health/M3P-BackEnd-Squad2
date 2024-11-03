package br.com.pvv.senai.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.pvv.senai.entity.Usuario;
import br.com.pvv.senai.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserRepository repository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

			var token = this.recoverToken(request);
			if (token != null) {
				var subject = tokenService.validateToken(token);
				Usuario user = repository.findByEmail(subject).orElse(null);

				// Validação para garantir que o usuário só possa acessar seus próprios dados
				String requestUri = request.getRequestURI();
				String[] uriParts = requestUri.split("/");
				if (uriParts.length > 2 && "pacientes".equals(uriParts[1])) {
					Long idFromUri = Long.valueOf(uriParts[2]); // A id do URI deve ser convertida para Long

					// Comparação direta com o valor primitivo
					if (user.getPaciente().getId() != idFromUri) { // Usando != para comparação de primitivas
						response.setStatus(HttpStatus.FORBIDDEN.value());
						return; // Bloqueia o acesso
					}
				}

				var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	private String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if (authHeader == null)
			return null;

		return authHeader.replace("Bearer ", "");
	}

}
