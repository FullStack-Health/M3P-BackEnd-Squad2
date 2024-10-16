package br.com.pvv.senai.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.pvv.senai.entity.Consulta;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ConsultaDto extends GenericDto<Consulta> {

	private long Id;
	@NotNull
	@Size(min = 8, max = 64)
	private String motivoConsulta;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataConsulta;
	@NotNull
	@DateTimeFormat(pattern = "hh:mm:ss")
	private LocalTime horaDaConsulta;
	@NotNull
	@Size(min = 16, max = 1024)
	private String descricaoDoProblema;
	private String medicacaoReceitada;
	@Size(min = 16, max = 256)
	private String dosagemEPrecausoes;
	@NotNull
	private PacienteDto paciente;

	@Override
	protected Class<Consulta> getType() {
		return Consulta.class;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getMotivoConsulta() {
		return motivoConsulta;
	}

	public void setMotivoConsulta(String motivoConsulta) {
		this.motivoConsulta = motivoConsulta;
	}

	public LocalDate getDataConsulta() {
		return dataConsulta;
	}

	public void setDataConsulta(LocalDate dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

	public LocalTime getHoraDaConsulta() {
		return horaDaConsulta;
	}

	public void setHoraDaConsulta(LocalTime horaDaConsulta) {
		this.horaDaConsulta = horaDaConsulta;
	}

	public String getDescricaoDoProblema() {
		return descricaoDoProblema;
	}

	public void setDescricaoDoProblema(String descricaoDoProblema) {
		this.descricaoDoProblema = descricaoDoProblema;
	}

	public String getMedicacaoReceitada() {
		return medicacaoReceitada;
	}

	public void setMedicacaoReceitada(String medicacaoReceitada) {
		this.medicacaoReceitada = medicacaoReceitada;
	}

	public String getDosagemEPrecausoes() {
		return dosagemEPrecausoes;
	}

	public void setDosagemEPrecausoes(String dosagemEPrecausoes) {
		this.dosagemEPrecausoes = dosagemEPrecausoes;
	}

	public PacienteDto getPaciente() {
		return paciente;
	}

	public void setPaciente(PacienteDto paciente) {
		this.paciente = paciente;
	}

}
