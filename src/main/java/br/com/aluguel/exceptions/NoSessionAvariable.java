package br.com.aluguel.exceptions;

public class NoSessionAvariable extends Exception{
    
    public NoSessionAvariable(){
        super("Este Usuário ainda não iniciou uma sessão.");
    }
    
    public NoSessionAvariable(String msg){
        super(msg);
    }

    public NoSessionAvariable(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
