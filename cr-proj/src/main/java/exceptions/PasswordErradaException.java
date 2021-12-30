package exceptions;

import reparacoes.Passo;

public class PasswordErradaException extends Exception{

    public PasswordErradaException (){
        super();
    }

    public PasswordErradaException (String message){
        super(message);
    }
}
