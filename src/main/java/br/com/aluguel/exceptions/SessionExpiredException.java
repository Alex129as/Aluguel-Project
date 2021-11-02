package br.com.aluguel.exceptions;

public class SessionExpiredException extends Exception {
    
    private static final long serialVersionUID = 2L;

    public SessionExpiredException(){
        super("Sessão do Usuário está expirada. Faça Login Novamente!");
    }

    public SessionExpiredException(String msg){
        super(msg);
    }

    public SessionExpiredException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
