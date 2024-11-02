package br.com.pvv.senai.controller.filter;

import java.util.Map;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.pvv.senai.entity.Consulta;

public class ConsultaFilter implements IFilter<Consulta> {

	private String reason;
	private int pageSize;
	private int pageNumber;

	public ConsultaFilter(Map<String, String> params) {
		setReason(params.get("reason"));
		setPageNumber(params.get("pageNumber") != null ? Integer.parseInt(params.get("pageNumber")) : 0);
		setPageSize(params.get("pageSize") != null ? Integer.parseInt(params.get("pageSize")) : 10);
	}

	@Override
	public Example<Consulta> example() {

		var matcher = ExampleMatcher.matchingAny().withMatcher("reason", match -> match.ignoreCase().contains())
				.withIgnoreNullValues();

		var probe = new Consulta();
		probe.setReason(getReason());

		return Example.of(probe, matcher);
	}

	@Override
	public Pageable getPagination() {
		return PageRequest.of(getPageNumber(), getPageSize());
	}

	public final String getReason() {
		return reason;
	}

	public final void setReason(String reason) {
		this.reason = reason;
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

}
