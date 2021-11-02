package br.com.aluguel.exceptions;

public class NoDataFoundException extends Exception{
    
    private static final long serialVersionUID = 1L;

    public NoDataFoundException(){
        super("Nenhum Resultado Encontrado na Pesquisa!");
    }

    public NoDataFoundException(String menssage){
        super(menssage);
    }

    public NoDataFoundException(String menssage, Throwable throwable){
        super(menssage, throwable);
    }

}
