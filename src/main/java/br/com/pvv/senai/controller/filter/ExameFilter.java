package br.com.pvv.senai.controller.filter;

import java.util.Map;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.pvv.senai.entity.Exame;

public class ExameFilter implements IFilter<Exame> {

	private String nome;
	private int pageSize;
	private int pageNumber;

	public ExameFilter(Map<String, String> params) {
		setNome(params.get("nome"));
		setPageNumber(params.get("pageNumber") != null ? Integer.parseInt(params.get("pageNumber")) : 0);
		setPageSize(params.get("pageSize") != null ? Integer.parseInt(params.get("pageSize")) : 10);
	}

	@Override
	public Example<Exame> example() {

		var matcher = ExampleMatcher.matchingAny().withMatcher("nome", match -> match.ignoreCase().contains())
				.withIgnoreNullValues();

		var probe = new Exame();
		probe.setNome(getNome());

		return Example.of(probe, matcher);
	}

	@Override
	public Pageable getPagination() {
		return PageRequest.of(getPageNumber(), getPageSize());
	}

	public final int getPageSize() {
		return pageSize;
	}

	public final void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public final int getPageNumber() {
		return pageNumber;
	}

	public final void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public final String getNome() {
		return nome;
	}

	public final void setNome(String nome) {
		this.nome = nome;
	}

}
