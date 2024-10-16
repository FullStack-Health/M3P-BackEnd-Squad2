package br.com.pvv.senai.exceptions;

public class NotRequiredByProjectException extends Exception {

	public NotRequiredByProjectException() {
		super("Método não implementado por não ser requisitado pelo projeto. \n\n\t\tSe não tá no projeto, \n\t\tpra que tu tá usando? deixa de ser abestado! \n\t\t\t:D");
	}

}
