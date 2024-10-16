package br.com.pvv.senai.entity;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Paciente implements IEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(length = 64, nullable = false)
	private String nome; // 8-64
	@Column(nullable = false)
	private String genero;
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataNascimento;
	@Column(length = 14) // 000.000.000-00
	@NumberFormat(pattern = "###.###.###-##")
	private String CPF;
	@Column(length = 20)
	private String RG;
	@Column(nullable = false)
	private String estadoCivil;
	@Column(nullable = false)
	@NumberFormat(pattern = "(##) # ####-####")
	private String telefone;
	@Column()
	private String email; // regex \w+@\w+
	@Column(nullable = false, length = 64) // 8-64
	private String naturalidade;
	@Column(nullable = false)
	@NumberFormat(pattern = "(##) # ####-####")
	private String cttDeEmergencia;
	@Column()
	private String alergias;
	@Column()
	private String cuidadosEspecificos;
	@Column()
	private String convenio;
	@Column()
	private String numeroConvenio;
	@Column()
	private Date validadeConvenio;
	@OneToOne()
	private Endereco endereco;

	@OneToOne(cascade = CascadeType.DETACH, orphanRemoval = true)
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;

	@OneToMany
	private List<Consulta> consultas;
	@OneToMany
	private List<Exame> exames;

	public List<Consulta> getConsultas() {
		return consultas;
	}

	public String getConvenio() {
		return convenio;
	}

	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}

	public List<Exame> getExames() {
		return exames;
	}

	public void setExames(List<Exame> exames) {
		this.exames = exames;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
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

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
