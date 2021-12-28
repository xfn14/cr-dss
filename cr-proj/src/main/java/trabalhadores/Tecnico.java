package trabalhadores;

import java.io.Serializable;

public class Tecnico extends Trabalhador implements Serializable {
    private boolean available;
    private String idPedido;

    public Tecnico(String idTecnico, String passe) {
        super(idTecnico, passe);
        this.available = false;
        this.idPedido = "";
    }

    public Tecnico(Tecnico tecnico) {
        super(tecnico);
        this.available = tecnico.isAvailable();
        this.idPedido = tecnico.getIdPedido();
    }

    public String getIdPedido() {
        return this.idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Tecnico clone() {
        return new Tecnico(this);
    }
}
