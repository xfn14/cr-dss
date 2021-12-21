package reparacoes;

import java.util.Objects;

public class Reparacao {
    private String idReparacao;

    public Reparacao(){
        this.idReparacao = "";
    }

    public Reparacao(String idReparacao){
        this.idReparacao = idReparacao;
    }

    public Reparacao(Reparacao reparacao){
        this.idReparacao = reparacao.getIdReparacao();
    }

    public String getIdReparacao() {
        return idReparacao;
    }

    public void setIdReparacao(String idReparacao) {
        this.idReparacao = idReparacao;
    }

    public Reparacao clone(){
        return new Reparacao(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reparacao reparacao = (Reparacao) o;
        return this.idReparacao.equals(reparacao.getIdReparacao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idReparacao);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Reparacao{");
        sb.append("idReparacao='").append(this.idReparacao).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
