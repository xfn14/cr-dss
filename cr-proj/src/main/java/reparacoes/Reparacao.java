package reparacoes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reparacao implements Serializable {
    private String idReparacao;
    private String idPlanoTrabalho;
    private Estado estado;
    private List<Passo> passos; //TODO: Registar passos
    private double dinheiroGasto;


    public Reparacao() {
        this.idReparacao = "";
        this.idPlanoTrabalho = "";
        this.estado = Estado.DECORRER;
        this.passos = new ArrayList<>();
        this.dinheiroGasto = 0;
    }

    public Reparacao(String idReparacao, String idPlanoTrabalho) {
        this.idReparacao = idReparacao;
        this.idPlanoTrabalho = idPlanoTrabalho;
    }

    public Reparacao(Reparacao reparacao) {
        this.idReparacao = reparacao.getIdReparacao();
        this.idPlanoTrabalho = reparacao.getIdPlanoTrabalho();
    }

    public enum Estado {
        DECORRER,
        PAUSA,
        FINALIZADA,
        CANCELADA
    }

    public void registaPasso(double horas, double custoPecas) {
        Passo passo = new Passo(horas, custoPecas);
        this.passos.add(passo);
        this.dinheiroGasto += custoPecas;
    }

    public String getIdReparacao() {
        return this.idReparacao;
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

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado e) {
        this.estado = e;
    }

    public List<Passo> getPassos() {
        return new ArrayList<>(this.passos);
    }

    public void setPassos(List<Passo> passos) {
        this.passos = new ArrayList<>(passos);
    }

    public Reparacao clone() {
        return new Reparacao(this);
    }
}
