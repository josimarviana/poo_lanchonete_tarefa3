package br.com.appdahora.lanchonete.domain.exception;

public class CPFInvalidoException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public CPFInvalidoException(String mensagem){
        super(mensagem);
    }
}
