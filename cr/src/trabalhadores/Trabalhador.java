package trabalhadores;

import java.util.Objects;

public class Trabalhador {
    private String idTrabalhador;

    public Trabalhador(){
        this.idTrabalhador = "";
    }

    public Trabalhador(String idTrabalhador) {
        this.idTrabalhador = idTrabalhador;
    }

    public Trabalhador(Trabalhador trabalhador){
        this.idTrabalhador = trabalhador.getIdTrabalhador();
    }

    public String getIdTrabalhador() {
        return this.idTrabalhador;
    }

    public void setIdTrabalhador(String idTrabalhador) {
        this.idTrabalhador = idTrabalhador;
    }

    public Trabalhador clone(){
        return new Trabalhador(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trabalhador that = (Trabalhador) o;
        return Objects.equals(this.idTrabalhador, that.getIdTrabalhador());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idTrabalhador);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Trabalhador{");
        sb.append("idTrabalhador='").append(this.idTrabalhador).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
