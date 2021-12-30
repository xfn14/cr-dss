package reparacoes;

import java.io.Serializable;
import java.time.Duration;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlanoTrabalho implements Serializable {
    private String idPlanoTrabalho;
    private String idTecnico;
    private List<Passo> passos; //TODO: Adicionar para um par de Passo,Bool que indica se já foi feito ou não
    private Estado estado;
    private double orcamento;
    private Duration duracaoTotal;

    public PlanoTrabalho(String idPlanoTrabalho, List<Passo> passos) {
        this.idPlanoTrabalho = idPlanoTrabalho;
        this.passos = passos;
        this.orcamento = 0;
        this.duracaoTotal = Duration.ZERO;
    }

    public PlanoTrabalho(String idPlanoTrabalho, String idTecnico) {
        this.idTecnico = idTecnico;
        this.idPlanoTrabalho = idPlanoTrabalho;
        this.passos = new ArrayList<>();
        this.estado = Estado.DECORRER;
        this.orcamento = 0;
        this.duracaoTotal = Duration.ZERO;
    }

    public PlanoTrabalho(PlanoTrabalho planoTrabalho) {
        this.idPlanoTrabalho = planoTrabalho.getIdPlanoTrabalho();
        this.idTecnico = planoTrabalho.getIdTecnico();
        this.passos = planoTrabalho.getPassos();
        this.estado = planoTrabalho.getEstado();
        this.orcamento = planoTrabalho.getOrcamento();
        this.duracaoTotal = planoTrabalho.getDuracaoTotal();
    }

    public Map.Entry<String,String[]> passoToEntry(int index){
        Passo passo = this.passos.get(index);
        if(passo.getSubPassos().size() == 0)
            return new AbstractMap.SimpleEntry<>(
                    "(" + passo.getDuration() + "/" + passo.getCustoPecas() + ") " + passo.getDescricao(), null
            );

        String[] subPassos = new String[passo.getSubPassos().size()];
        int i = 0;
        for(Passo p : passo.getSubPassos())
            subPassos[i++] = "(" + passo.getDuration() + "/" + passo.getCustoPecas() + ") " + p.getDescricao();
        return new AbstractMap.SimpleEntry<>(
                "(" + (index + 1) + "/" + this.passos.size() + ")",
                subPassos
        );
    }

    public void addPasso(double horas, double custoPecas, String descricao) {
        this.duracaoTotal = this.duracaoTotal.plus(Duration.ofHours((long) horas));
        Passo passo = new Passo(horas, custoPecas, descricao);
        this.passos.add(passo);
        this.orcamento += custoPecas;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void addSubPasso(int indexPasso, double horas, double custoPecas, String descricao) {
        if (indexPasso >= passos.size()) {
            Passo passo = new Passo(0, 0, descricao);
            passos.add(passo);
        }
        this.orcamento+=custoPecas;
        Passo passo = passos.get(indexPasso);
        passo.addSubPasso(horas, custoPecas, descricao);
    }

    public double getCustoPecasPasso(int indexPasso) {
        return this.passos.get(indexPasso).getCustoPecas();
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
        return this.orcamento;
    }

    public Duration getDuracaoTotal() {
        return this.duracaoTotal;
    }

    public String getIdTecnico() {
        return this.idTecnico;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public List<Passo> getPassos() {
        return new ArrayList<>(this.passos);
    }

    public enum Estado {
        AGUARDA_ACEITACAO,
        DECORRER,
        PAUSA,
        FINALIZADO,
        CANCELADO
    }


    @Override
    public String toString() {
        return "PlanoTrabalho{" +
                "idPlanoTrabalho='" + idPlanoTrabalho + '\'' +
                ", idTecnico='" + idTecnico + '\'' +
                ", passos=" + passos.stream().map(Passo::toString).collect(Collectors.toList()).toString() +
                ", estado=" + estado +
                ", orcamento=" + orcamento +
                ", duracaoTotal=" + duracaoTotal +
                '}';
    }
}
