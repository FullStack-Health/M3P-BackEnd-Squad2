package br.com.pvv.senai.model.dto;

import br.com.pvv.senai.entity.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class UsuarioUpdateDto extends GenericDto<Usuario> {

	private long Id;
	@NotNull
	@Size(max = 255)
	private String nome;
	@NotNull
	@Pattern(regexp = "\\(\\d{2}+\\) \\d{1}+ \\d{4}+-\\d{4}+")
	private String telefone;
	@NotNull
	@Email
	@Size(max = 255)
	private String email;
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date dataNascimento;
	@NotNull
	@Size(max = 14)
	private String cpf;

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	protected Class<Usuario> getType() {
		return Usuario.class;
	}

}
