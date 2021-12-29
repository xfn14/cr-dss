package trabalhadores;

import java.io.Serializable;
import java.time.Duration;

public class Tecnico extends Trabalhador implements Serializable {
    private boolean available;
    private String idPedido;
    private Duration trabalhoPorRealizar;

    public Tecnico(Trabalhador trabalhador) {
        super(trabalhador);
        this.available = true;
        this.idPedido = "";
        this.trabalhoPorRealizar = Duration.ZERO;
    }

    public Tecnico(String idTecnico, String passe) {
        super(idTecnico, passe);
        this.available = true;
        this.idPedido = "";
        this.trabalhoPorRealizar = Duration.ZERO;
    }

    public Tecnico(Tecnico tecnico) {
        super(tecnico);
        this.available = tecnico.isAvailable();
        this.idPedido = tecnico.getIdPedido();
        this.trabalhoPorRealizar = tecnico.getTrabalhoPorRealizar();
    }


    public void addHoras (long horas){
        this.trabalhoPorRealizar = this.trabalhoPorRealizar.plus(Duration.ofHours(horas));
    }

    public void minusHoras(long horas){
        this.trabalhoPorRealizar = this.trabalhoPorRealizar.minus(Duration.ofHours(horas));
    }


    public Duration getTrabalhoPorRealizar() {
        return trabalhoPorRealizar;
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
