package br.com.pvv.senai.exceptions;

public class PacienteUserNotFoundException extends Exception {

	public PacienteUserNotFoundException(String email) {
		super("Usuário não localizado para paciente : " + email);
	}
}
