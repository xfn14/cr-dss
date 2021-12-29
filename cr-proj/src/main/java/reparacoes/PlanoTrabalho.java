package reparacoes;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PlanoTrabalho implements Serializable {
    private String idPlanoTrabalho;
    private String idTecnico;
    private List<Passo> passos;
    private Estado estado;// TODO
    private double orcamento;
    private Duration duracaoTotal;

    public PlanoTrabalho(String idPlanoTrabalho, List<Passo> passos) {
        this.idPlanoTrabalho = idPlanoTrabalho;
        this.passos = passos;
        this.orcamento = 0;
        this.duracaoTotal=Duration.ZERO;
    }

    public PlanoTrabalho(String idPlanoTrabalho,String idTecnico) {
        this.idTecnico = idTecnico;
        this.idPlanoTrabalho = idPlanoTrabalho;
        this.passos = new ArrayList<>();
        this.estado = Estado.DECORRER;
        this.orcamento = 0;
    }

    public void addPasso(double horas, double custoPecas,String descricao) {
        this.duracaoTotal= this.duracaoTotal.plus(Duration.ofHours((long)horas));
        Passo passo = new Passo(horas,custoPecas,descricao);
        this.passos.add(passo);
        this.orcamento += passo.getCustoPecas();
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void addSubPasso(int indexPasso,double horas, double custoPecas){
        if (indexPasso >= passos.size()){
            Passo passo = new Passo(0,0);
            passos.add(passo);
        }
        Passo passo = passos.get(indexPasso);
        passo.addSubPasso(horas, custoPecas);
    }


    public double getCustoPecasPasso(int indexPasso){
        return this.passos.get(indexPasso).getCustoPecas();
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

    public double getOrcamento() {
        return orcamento;
    }

    public Duration getDuracaoTotal() {
        return duracaoTotal;
    }

    public String getIdTecnico() {
        return idTecnico;
    }
}
