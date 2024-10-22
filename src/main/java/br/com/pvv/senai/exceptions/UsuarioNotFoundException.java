package br.com.pvv.senai.exceptions;

public class UsuarioNotFoundException extends RuntimeException {

	public UsuarioNotFoundException() {
		super("Usuário não localizado.");
	}
	
}
