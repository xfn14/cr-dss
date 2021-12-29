package pedidos;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Contacto implements Serializable {
    private String idTrabalhador;
    private LocalDateTime dateTime;
    private Type type;

    public Contacto(String idTrabalhador, Type type) {
        this.idTrabalhador = idTrabalhador;
        this.dateTime = LocalDateTime.now();
        this.type = type;
    }

    public Contacto(Contacto contacto) {
        this.idTrabalhador = contacto.getIdTrabalhador();
        this.dateTime = contacto.getDateTime();
        this.type = contacto.getType();
    }

    public String getIdTrabalhador() {
        return this.idTrabalhador;
    }

    public void setIdTrabalhador(String idTrabalhador) {
        this.idTrabalhador = idTrabalhador;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Contacto clone() {
        return new Contacto(this);
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "trabalhador=" + this.idTrabalhador +
                ", tipo=" + this.type.toString() +
                "}";
    }

    enum Type {
        PRONTO_LEVANTAR,
        SEM_REPARACAO,
        VALOR_SUPERIOR,
        PEDIDO_ORCAMENTO
    }
}
