package reparacoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlanoTrabalho {
    private String idPlanoTrabalho;
    private List<Passo> passos;

    public PlanoTrabalho(String idPlanoTrabalho, List<Passo> passos){
        this.idPlanoTrabalho = idPlanoTrabalho;
        this.passos = new ArrayList<>();
    }

    public PlanoTrabalho(PlanoTrabalho planoTrabalho){
        this.idPlanoTrabalho = planoTrabalho.getIdPlanoTrabalho();
    }

    public String getIdPlanoTrabalho() {
        return this.idPlanoTrabalho;
    }

    public void setIdPlanoTrabalho(String idPlanoTrabalho) {
        this.idPlanoTrabalho = idPlanoTrabalho;
    }

    @Override
    public PlanoTrabalho clone(){
        return new PlanoTrabalho(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanoTrabalho that = (PlanoTrabalho) o;
        return Objects.equals(this.idPlanoTrabalho, that.getIdPlanoTrabalho());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idPlanoTrabalho);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PlanoTrabalho{");
        sb.append("idPlanoTrabalho='").append(this.idPlanoTrabalho).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
