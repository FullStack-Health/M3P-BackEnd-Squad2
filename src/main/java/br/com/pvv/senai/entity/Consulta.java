package br.com.pvv.senai.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Consulta implements IEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	@Column(nullable = false)
	private String motivoConsulta;
	@Column(nullable = false)
	private LocalDate dataConsulta;
	@Column(nullable = false)
	private LocalTime horaDaConsulta;
	@Column(nullable = false, length = 1024)
	private String descricaoDoProblema;
	@Column()
	private String medicacaoReceitada;
	@Column(length = 1024)
	private String dosagemEPrecausoes;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "id_paciente")
	private Paciente paciente;

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

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

}
