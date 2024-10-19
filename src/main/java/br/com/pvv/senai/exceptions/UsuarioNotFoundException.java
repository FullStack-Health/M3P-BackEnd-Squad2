package br.com.pvv.senai.exceptions;

public class UsuarioNotFoundException extends Exception {

	public UsuarioNotFoundException() {
		super("Usuário não localizado.");
	}
	
}
