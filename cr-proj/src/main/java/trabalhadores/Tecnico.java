package trabalhadores;

public class Tecnico extends Trabalhador {
    private boolean available;
    private String idPedido;

    public Tecnico(String idTecnico,String passe) {
        super(idTecnico,passe);
        this.available= false;
        this.idPedido = "";
    }

    public Tecnico (Tecnico tecnico){
        super(tecnico);
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

    public boolean isAvailable (){
        return isAvailable();
    }
}
