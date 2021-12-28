package reparacoes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Reparacao implements Serializable {
    private String idReparacao;
    private Estado estado;
    private List<Passo> passos; //TODO: Registar passos
    private double orcamento;
    private double orcamentoExpectavel;


    public Reparacao() {
        this.idReparacao = "";
        this.estado = Estado.DECORRER;
        this.passos = new ArrayList<>();
        this.orcamentoExpectavel = 0;
    }

    public Reparacao(String idReparacao, double orcamento) {
        this.idReparacao = idReparacao;
        this.orcamento = orcamento;
        this.orcamentoExpectavel = orcamento;
    }



    public enum Estado {
        DECORRER,
        PAUSA,
        AGURDA_ACEITACAO,
        FINALIZADA,
        CANCELADA
    }



    public boolean aguardaAceitacao(){
        return estado.equals(Estado.AGURDA_ACEITACAO);
    }

    public String getIdReparacao() {
        return this.idReparacao;
    }

    public int registaPasso(double horas, double custoPecas) {
        Passo passo = new Passo(horas, custoPecas);
        this.passos.add(passo);
        return passos.size()-1;
    }

    public void changeOrcamento (double diff){
        this.orcamento+= diff;
    }

    public boolean checkSuperior120(){
        double superior = orcamentoExpectavel*1.2;
        return orcamento > superior;
    }

    public void setIdReparacao(String idReparacao) {
        this.idReparacao = idReparacao;
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

}
