package reparacoes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlanoTrabalho implements Serializable {
    private String idPlanoTrabalho;
    private List<Passo> passos;
    private Estado estado;// TODO
    private double orcamento;

    public PlanoTrabalho(String idPlanoTrabalho, List<Passo> passos) {
        this.idPlanoTrabalho = idPlanoTrabalho;
        this.passos = passos;
        this.orcamento = 0;
    }

    public PlanoTrabalho(String idPlanoTrabalho) {
        this.idPlanoTrabalho = idPlanoTrabalho;
        this.passos = new ArrayList<>();
        this.estado = Estado.DECORRER;
        this.orcamento = 0;
    }

    public void addPasso(Passo passo) {
        this.passos.add(passo);
        this.orcamento += passo.getCustoPecas();
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void addSubPasso(double horas, double custoPecas) {
        int length = passos.size();
        Passo passo = passos.get(length - 1);
        passo.addSubPasso(horas, custoPecas);
    }

    public enum Estado {
        AGUARDA_ACEITACAO,
        DECORRER,
        PAUSA,
        FINALIZADO,
        CANCELADO
    }

    public PlanoTrabalho(PlanoTrabalho planoTrabalho) {
        this.idPlanoTrabalho = planoTrabalho.getIdPlanoTrabalho();
    }

    public String getIdPlanoTrabalho() {
        return this.idPlanoTrabalho;
    }

    public void setIdPlanoTrabalho(String idPlanoTrabalho) {
        this.idPlanoTrabalho = idPlanoTrabalho;
    }

    @Override
    public PlanoTrabalho clone() {
        return new PlanoTrabalho(this);
    }
}
