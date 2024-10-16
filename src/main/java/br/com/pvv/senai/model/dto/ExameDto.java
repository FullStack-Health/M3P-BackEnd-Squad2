package br.com.pvv.senai.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.pvv.senai.entity.Exame;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ExameDto extends GenericDto<Exame> {

	private long id;

	@Size(min = 8, max = 64)
	private String nome;
	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
	private LocalDate dataExame;
	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "hh:mm:ss")
	private LocalTime horaExame;
	@NotNull
	@Size(min = 4, max = 32)
	private String tipo;
	@NotNull
	@Size(min = 4, max = 32)
	private String laboratorio;
	private String URL;
	@Size(min = 16, max = 1024)
	private String resultados;
	@NotNull
	private PacienteDto paciente;

	@Override
	protected Class<Exame> getType() {
		return Exame.class;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getDataExame() {
		return dataExame;
	}

	public void setDataExame(LocalDate dataExame) {
		this.dataExame = dataExame;
	}

	public LocalTime getHoraExame() {
		return horaExame;
	}

	public void setHoraExame(LocalTime horaExame) {
		this.horaExame = horaExame;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getResultados() {
		return resultados;
	}

	public void setResultados(String resultados) {
		this.resultados = resultados;
	}

	public PacienteDto getPaciente() {
		return paciente;
	}

	public void setPaciente(PacienteDto paciente) {
		this.paciente = paciente;
	}

}
