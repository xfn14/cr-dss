package exceptions;

public class JaExisteException extends  Exception{
    public JaExisteException(){
        super();
    }

    public JaExisteException(String message){
        super(message);
    }
}
