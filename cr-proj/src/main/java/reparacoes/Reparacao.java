package reparacoes;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Reparacao implements Serializable {
    private String idReparacao;
    private final String idTecnico;
    private final double orcamentoExpectavel;
    private Estado estado;
    private Duration duracaoTotal;
    private List<Passo> passos;
    private double orcamento;

    public Reparacao(String idReparacao, String idTecnico, double orcamento) {
        this.idReparacao = idReparacao;
        this.idTecnico = idTecnico;
        this.orcamento = orcamento;
        this.orcamentoExpectavel = orcamento;
        this.duracaoTotal = Duration.ZERO;
        this.estado = Estado.AGUARDA_INICIO;
    }

    //TODO Add motivo para o Estado, para saber a razão de estar em pausa|cancelado,ect...
    public enum Estado {
        AGUARDA_INICIO,
        DECORRER,
        PAUSA,
        //TODO: Modificar Pausa, quando dá logout, passar as DECORRER para PAUSA_TEMPO
        //PAUSA_TEMPO ->
        //PAUSA_PECAS
        AGUARDA_ACEITACAO,
        FINALIZADA,
        CANCELADA;
    }



    public int nPassoAtual(){
        return this.passos.size()-1;
    }

    public boolean aguardaAceitacao() {
        return this.estado.equals(Estado.AGUARDA_ACEITACAO);
    }

    public String getIdReparacao() {
        return this.idReparacao;
    }

    public void setIdReparacao(String idReparacao) {
        this.idReparacao = idReparacao;
    }

    public int registaPasso(double horas, double custoPecas) {
        Passo passo = new Passo(horas, custoPecas);
        this.duracaoTotal = this.duracaoTotal.plus(Duration.ofHours((long) horas));
        this.passos.add(passo);
        return passos.size() - 1;
    }

    public void changeOrcamento(double diff) {
        this.orcamento += diff;
    }

    public boolean checkSuperior120() {
        double superior = orcamentoExpectavel * 1.2;
        return orcamento > superior;
    }

    public void reparacaoAceite() {
        setEstado(Estado.PAUSA);
        this.orcamento = this.orcamentoExpectavel;
    }

    public void addDescricoesToList(List<String> list) {
        for (Passo passo : passos)
            list.add(passo.getDescricao());
    }

    public boolean emPausa() {
        return this.estado.equals(Estado.PAUSA);
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
        return this.duracaoTotal;
    }

    public String getIdTecnico() {
        return this.idTecnico;
    }
}
