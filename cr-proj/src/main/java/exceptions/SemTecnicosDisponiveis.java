package exceptions;

public class SemTecnicosDisponiveis extends Exception{
    public SemTecnicosDisponiveis(){
        super("Nao existem tecnicos disponiveis");
    }
    
}
