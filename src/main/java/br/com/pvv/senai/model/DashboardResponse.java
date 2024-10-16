package br.com.pvv.senai.model;

public class DashboardResponse {

	private long qtdPacientes;
	private long qtdConsultas;
	private long qtdExames;

	public long getQtdPacientes() {
		return qtdPacientes;
	}

	public void setQtdPacientes(long qtdPacientes) {
		this.qtdPacientes = qtdPacientes;
	}

	public long getQtdConsultas() {
		return qtdConsultas;
	}

	public void setQtdConsultas(long qtdConsultas) {
		this.qtdConsultas = qtdConsultas;
	}

	public long getQtdExames() {
		return qtdExames;
	}

	public void setQtdExames(long qtdExames) {
		this.qtdExames = qtdExames;
	}

}
