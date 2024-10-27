package br.com.pvv.senai.security;

import br.com.pvv.senai.entity.Usuario;
import br.com.pvv.senai.enums.Perfil;
import br.com.pvv.senai.repository.UserRepository;
import br.com.pvv.senai.service.GenericService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService extends GenericService<Usuario> {

	@Autowired
	private UserRepository repository;

	@Autowired
	private EntityManager em;

	@Override
	public Page<Usuario> paged(Example<Usuario> example, Pageable pageable) {
//		return super.paged(example, pageable);
		var cb = em.getCriteriaBuilder();
		var q = cb.createQuery(Usuario.class);
		var p = q.from(Usuario.class);

		var userExample = example.getProbe();

		cb.notEqual(p.get("perfil"), Perfil.PACIENTE);
		Predicate withEmail = null;
		Predicate withNome = null;

		if (userExample.getEmail() != null)
			withEmail = cb.like(p.get("email"), "%" + userExample.getEmail() + "%");
		if (userExample.getNome() != null)
			withNome = cb.like(p.get("nome"), "%" + userExample.getNome() + "%");

		Predicate withWhere = null;
		if (withEmail != null && withNome != null)
			withWhere = cb.or(withEmail, withNome);
		else if (withEmail != null || withNome != null)
			if (withEmail != null)
				withWhere = withEmail;
			else
				withWhere = withNome;

		CriteriaQuery<Usuario> select = null;
		if (withWhere != null)
			select = q.where(withWhere).select(p);
		else
			select = q.select(p);

		var result = em.createQuery(select).getResultList();

		return new PageImpl<>(result, pageable, result.size());
	}

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

	public long count() { return repository.count(); }
}
