package reparacoes;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Reparacao implements Serializable {
    private String idReparacao;
    private final String idTecnico;
    private Estado estado;
    private Duration duracaoTotal;
    private List<Passo> passos; //TODO: Registar passos
    private double orcamento;
    private final double orcamentoExpectavel;

    public Reparacao(String idReparacao, String idTecnico,double orcamento) {
        this.idTecnico = idTecnico;
        this.idReparacao = idReparacao;
        this.orcamento = orcamento;
        this.orcamentoExpectavel = orcamento;
        this.duracaoTotal = Duration.ZERO;
        this.estado=Estado.AGUARDA_INICIO;
    }

    public enum Estado {
        AGUARDA_INICIO,
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


    public boolean emPausa(){
        return this.estado.equals(Estado.PAUSA);
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
