package trabalhadores;

public class Tecnico extends Trabalhador {
    private String idPedido;

    public Tecnico(String idTecnico) {
        super(idTecnico);
        this.idPedido = "";
    }

    public Tecnico (Tecnico tecnico){
        super(tecnico.getIdTrabalhador());
        this.idPedido = tecnico.getIdPedido();
    }

    public String getIdPedido() {
        return this.idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public Tecnico clone(){
        return new Tecnico(this);
    }
}
