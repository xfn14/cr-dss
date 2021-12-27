package trabalhadores;

public class Gestor extends Trabalhador {
    public Gestor(String idGestor,String passe){
        super(idGestor,passe);
    }

    public Gestor(Gestor gestor){
        super(gestor);
    }

    public Gestor clone(){
        return new Gestor(this);
    }
}
