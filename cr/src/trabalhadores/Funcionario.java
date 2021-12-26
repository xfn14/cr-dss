package trabalhadores;

public class Funcionario extends Trabalhador {
    public Funcionario(String idFuncionario,String passe) {
        super(idFuncionario,passe);
    }

    public Funcionario (Funcionario funcionario){
        super(funcionario);
    }

    public Funcionario clone(){
        return new Funcionario(this);
    }
}
