package trabalhadores;

import java.io.Serializable;

public class Gestor extends Trabalhador implements Serializable {
    public Gestor(Trabalhador trabalhador) {
        super(trabalhador);
    }

    public Gestor(String idGestor, String passe) {
        super(idGestor, passe);
    }

    public Gestor(Gestor gestor) {
        super(gestor);
    }

    public Gestor clone() {
        return new Gestor(this);
    }
}
