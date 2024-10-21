package br.com.pvv.senai.model;

import java.util.List;

import br.com.pvv.senai.entity.Consulta;
import br.com.pvv.senai.entity.Exame;

public class ProntuarioDetails {

	private String name;
	private String convenio;
	private String cttDeEmergencia;
	private List<Exame> exames;
	private List<Consulta> consultas;

	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = name;
	}

	public String getConvenio() {
		return convenio;
	}

	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}

	public String getCttDeEmergencia() {
		return cttDeEmergencia;
	}

	public void setCttDeEmergencia(String cttDeEmergencia) {
		this.cttDeEmergencia = cttDeEmergencia;
	}

	public List<Exame> getExames() {
		return exames;
	}

	public void setExames(List<Exame> exames) {
		this.exames = exames;
	}

	public List<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}

}
