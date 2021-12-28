package reparacoes;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Reparacao implements Serializable {
    private String idReparacao;
    private String idTecnico;
    private Estado estado;
    private Duration duracaoTotal;
    private List<Passo> passos; //TODO: Registar passos
    private double orcamento;
    private double orcamentoExpectavel;


    public Reparacao() {
        this.idReparacao = "";
        this.estado = Estado.DECORRER;
        this.passos = new ArrayList<>();
        this.orcamentoExpectavel = 0;
        this.duracaoTotal=Duration.ZERO;
    }

    public Reparacao(String idReparacao, String idTecnico,double orcamento) {
        this.idReparacao = idReparacao;
        this.orcamento = orcamento;
        this.orcamentoExpectavel = orcamento;
        this.duracaoTotal = Duration.ZERO;
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
        this.duracaoTotal = this.duracaoTotal.plus(Duration.ofHours((long) horas));
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


    public void reparacaoAceite (){
        setEstado(Estado.PAUSA);
        this.orcamento = this.orcamentoExpectavel;
    }


    public void addDescricoesToList (List<String> list){
        for (Passo passo : passos){
            String descricao = passo.getDescricao();
            list.add(descricao);
        }
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

    public Duration getDuracaoTotal() {
        return duracaoTotal;
    }

    public String getIdTecnico() {
        return idTecnico;
    }



}
