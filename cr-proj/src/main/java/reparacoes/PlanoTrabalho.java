package reparacoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlanoTrabalho {
    private String idPlanoTrabalho;
    private List<Passo> passos;
    private Estado estado;// TODO
    private double orcamento;

    public PlanoTrabalho(String idPlanoTrabalho, List<Passo> passos){
        this.idPlanoTrabalho = idPlanoTrabalho;
        this.passos = passos;
        this.orcamento = 0;
    }

    public PlanoTrabalho(String idPlanoTrabalho){
        this.idPlanoTrabalho = idPlanoTrabalho;
        this.passos = new ArrayList<>();
        this.estado = Estado.DECORRER;
        this.orcamento = 0;
    }

    public void addPasso (Passo passo){
        this.passos.add(passo);
        this.orcamento += passo.getCustoPecas();
    }

    public void setEstado (Estado estado){
        this.estado = estado;
    }

    public void addSubPasso(double horas, double custoPecas) {
        int length = passos.size();
        Passo passo = passos.get(length-1);
        passo.addSubPasso(horas,custoPecas);
    }


    public enum Estado {
        AGUARDA_ACEITACAO,
        DECORRER,
        PAUSA,
        FINALIZADO,
        CANCELADO     
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
