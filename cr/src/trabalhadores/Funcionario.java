package trabalhadores;

public class Funcionario extends Trabalhador {

    public Funcionario(){
        super();
    }

    public Funcionario(String idFuncionario) {
        super(idFuncionario);
    }

    public Funcionario (Funcionario funcionario){
        super(funcionario.getIdTrabalhador());
    }

    public Funcionario clone(){
        return new Funcionario(this);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
