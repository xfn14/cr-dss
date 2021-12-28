package trabalhadores;

import java.io.Serializable;

public class Funcionario extends Trabalhador implements Serializable {
    public Funcionario(Trabalhador trabalhador) {
        super(trabalhador);
    }

    public Funcionario(String idFuncionario, String passe) {
        super(idFuncionario, passe);
    }

    public Funcionario(Funcionario funcionario) {
        super(funcionario);
    }

    public Funcionario clone() {
        return new Funcionario(this);
    }
}
