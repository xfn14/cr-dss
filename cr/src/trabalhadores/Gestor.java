package trabalhadores;

public class Gestor extends Trabalhador {
    public Gestor(String idGestor){
        super(idGestor);
    }

    public Gestor(Gestor gestor){
        super(gestor.getIdTrabalhador());
    }

    public Gestor clone(){
        return new Gestor(this);
    }
}
