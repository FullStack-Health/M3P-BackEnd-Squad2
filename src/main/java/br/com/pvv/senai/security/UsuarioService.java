package br.com.pvv.senai.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.pvv.senai.entity.Usuario;
import br.com.pvv.senai.repository.UserRepository;
import br.com.pvv.senai.service.GenericService;

@Service
public class UsuarioService extends GenericService<Usuario> {

	@Autowired
	private UserRepository repository;

	@Override
	public JpaRepository<Usuario, Long> getRepository() {
		return this.repository;
	}

	public boolean has(String email) {
		var example = makeExample(email);
		return this.repository.exists(example);
	}
	
	public Optional<Usuario> findByEmail(String email) {
		var example = makeExample(email);
		return this.repository.findOne(example);
	}
	
	private Example<Usuario> makeExample(String email) {
		ExampleMatcher matcher = ExampleMatcher.matchingAny().withIgnoreNullValues();
		Usuario u = new Usuario();
		u.setEmail(email);
		Example example = Example.of(u, matcher);
		return example;
	}

}
