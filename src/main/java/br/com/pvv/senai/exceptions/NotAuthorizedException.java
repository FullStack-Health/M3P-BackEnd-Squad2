package br.com.pvv.senai.exceptions;

public class NotAuthorizedException extends Exception {
	public NotAuthorizedException() {
		super("Não autorizado por requisições de projeto.");
	}
}
