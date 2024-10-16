package br.com.pvv.senai.model.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.pvv.senai.entity.Paciente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PacienteDto extends GenericDto<Paciente> {

	private long id;
	@NotNull
	@Size(min = 8, max = 64)
	private String nome;
	@NotNull
	private String genero;
	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
	private Date dataNascimento;
	@NotNull
	@Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")
	private String CPF;
	@NotNull
	@Size(max = 20)
	private String RG;
	@NotNull
	private String estadoCivil;
	@NotNull
	@Pattern(regexp = "\\(\\d{2}\\) \\d{1} \\d{4}-\\d{4}")
	private String telefone;
	@Email
	private String email;
	@NotNull
	@Size(min = 8, max = 64)
	private String naturalidade;
	@NotNull
	@Pattern(regexp = "\\(\\d{2}\\) \\d{1} \\d{4}-\\d{4}")
	private String cttDeEmergencia;
	private String alergias;
	private String cuidadosEspecificos;
	private String convenio;
	private String numeroConvenio;
	private Date validadeConvenio;
	private EnderecoDto endereco;

	@Override
	protected Class<Paciente> getType() {
		return Paciente.class;
	}

	public String getConvenio() {
		return convenio;
	}

	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		id = id;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getRG() {
		return RG;
	}

	public void setRG(String rG) {
		RG = rG;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getCttDeEmergencia() {
		return cttDeEmergencia;
	}

	public void setCttDeEmergencia(String cttDeEmergencia) {
		this.cttDeEmergencia = cttDeEmergencia;
	}

	public String getAlergias() {
		return alergias;
	}

	public void setAlergias(String alergias) {
		this.alergias = alergias;
	}

	public String getCuidadosEspecificos() {
		return cuidadosEspecificos;
	}

	public void setCuidadosEspecificos(String cuidadosEspecificos) {
		this.cuidadosEspecificos = cuidadosEspecificos;
	}

	public String getNumeroConvenio() {
		return numeroConvenio;
	}

	public void setNumeroConvenio(String numeroConvenio) {
		this.numeroConvenio = numeroConvenio;
	}

	public Date getValidadeConvenio() {
		return validadeConvenio;
	}

	public void setValidadeConvenio(Date validadeConvenio) {
		this.validadeConvenio = validadeConvenio;
	}

	public EnderecoDto getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDto endereco) {
		this.endereco = endereco;
	}

}
