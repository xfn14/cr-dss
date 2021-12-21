package trabalhadores;

public class Gestor extends Trabalhador {
    public Gestor(){
        super();
    }

    public Gestor(String idGestor){
        super(idGestor);
    }

    public Gestor(Gestor gestor){
        super(gestor.getIdTrabalhador());
    }

    public Gestor clone(){
        return new Gestor(this);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
