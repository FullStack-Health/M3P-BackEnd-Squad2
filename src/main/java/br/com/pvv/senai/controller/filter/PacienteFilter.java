package br.com.pvv.senai.controller.filter;

import java.util.Map;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.pvv.senai.entity.Paciente;

public class PacienteFilter implements IFilter<Paciente> {

	private String nome;
	private String telefone;
	private String email;
	private int pageSize;
	private int pageNumber;

	public PacienteFilter(Map<String, String> params) {
		this.setNome(params.get("nome"));
		this.setTelefone(params.get("telefone"));
		this.setEmail(params.get("email"));
		this.setPageNumber(params.get("pageNumber") != null ? Integer.parseInt(params.get("pageNumber")) : 0);
		this.setPageSize(params.get("pageSize") != null ? Integer.parseInt(params.get("pageSize")) : 10);
	}

	@Override
	public Pageable getPagination() {
		return PageRequest.of(pageNumber, pageSize);
	}

	@Override
	public Example<Paciente> example() {

		ExampleMatcher matcher = ExampleMatcher.matchingAny()
				.withMatcher("nome", match -> match.ignoreCase().contains())
				.withMatcher("telefone", match -> match.exact())
				.withMatcher("email", match -> match.ignoreCase().contains()) //
				.withIgnorePaths("id") //
				.withIgnoreNullValues();

		Paciente paciente = new Paciente();
		paciente.setNome(this.getNome());
		paciente.setTelefone(this.getTelefone());
		paciente.setEmail(this.getTelefone());

		return Example.of(paciente, matcher);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
