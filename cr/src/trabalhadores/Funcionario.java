package trabalhadores;

public class Funcionario extends Trabalhador {
    public Funcionario(String idFuncionario) {
        super(idFuncionario);
    }

    public Funcionario (Funcionario funcionario){
        super(funcionario.getIdTrabalhador());
    }

    public Funcionario clone(){
        return new Funcionario(this);
    }
}
