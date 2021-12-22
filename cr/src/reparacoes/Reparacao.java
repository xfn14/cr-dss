package reparacoes;

import java.util.Objects;

public class Reparacao {
    private String idReparacao;
    private String idPlanoTrabalho;
    private Estado estado;

    public Reparacao(){
        this.idReparacao = "";
        this.idPlanoTrabalho = "";

    }

    public Reparacao(String idReparacao, String idPlanoTrabalho){
        this.idReparacao = idReparacao;
        this.idPlanoTrabalho = idPlanoTrabalho;
    }

    public Reparacao(Reparacao reparacao){
        this.idReparacao = reparacao.getIdReparacao();
        this.idPlanoTrabalho = reparacao.getIdPlanoTrabalho();
    }

    public enum Estado {
        DECORRER,
        PAUSA,
        FINALIZADA,
        CANCELADA
    }

    public String getIdReparacao() {
        return idReparacao;
    }

    public void setIdReparacao(String idReparacao) {
        this.idReparacao = idReparacao;
    }

    public String getIdPlanoTrabalho() {
        return this.idPlanoTrabalho;
    }

    public void setIdPlanoTrabalho(String idPlanoTrabalho) {
        this.idPlanoTrabalho = idPlanoTrabalho;
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
