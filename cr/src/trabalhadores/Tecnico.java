package trabalhadores;

public class Tecnico extends Trabalhador {
    public Tecnico(){
        super();
    }

    public Tecnico(String idTecnico) {
        super(idTecnico);
    }

    public Tecnico (Tecnico tecnico){
        super(tecnico.getIdTrabalhador());
    }

    public Tecnico clone(){
        return new Tecnico(this);
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
