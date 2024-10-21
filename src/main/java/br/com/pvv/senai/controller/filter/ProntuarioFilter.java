package br.com.pvv.senai.controller.filter;

import java.util.Map;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.pvv.senai.entity.Paciente;

public class ProntuarioFilter implements IFilter<Paciente> {

	private String name;
	private String numeroRegistro;
	private int pageSize;
	private int pageNumber;

	public ProntuarioFilter(Map<String, String> params) {
		this.setName(params.get("name"));
		this.setNumeroRegistro(params.get("numeroRegistro"));
		this.setPageNumber(params.get("pageNumber") != null ? Integer.parseInt(params.get("pageNumber")) : 0);
		this.setPageSize(params.get("pageSize") != null ? Integer.parseInt(params.get("pageSize")) : 10);
	}

	@Override
	public Example<Paciente> example() {

		ExampleMatcher matcher = ExampleMatcher.matchingAny()
				.withMatcher("name", match -> match.contains().ignoreCase())
				.withMatcher("numeroRegistro", match -> match.exact()) //
				.withIgnorePaths("id") //
				.withIgnoreNullValues();

		Paciente paciente = new Paciente();

		if (this.getName() != null)
			paciente.setName(this.getName());
		if (this.getNumeroRegistro() != null)
			paciente.setId(Integer.valueOf(this.getNumeroRegistro()));

		return Example.of(paciente, matcher);
	}

	@Override
	public Pageable getPagination() {
		return PageRequest.of(this.pageNumber, this.pageSize);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
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
