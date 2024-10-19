package br.com.pvv.senai.exceptions;

public class EmailViolationExistentException extends Exception {

	public EmailViolationExistentException() {
		super("E-mail já existente");
		
	}
	
}
