package reparacoes;

import exceptions.InvalidIdException;
import exceptions.ValorSuperior;

import java.io.Serializable;
import java.time.Duration;
import java.util.*;

public class Reparacoes implements IReparacoes, Serializable {
    private final Map<String, Reparacao> reparacaoMap;
    private final Map<String, PlanoTrabalho> planoTrabalhoMap;

    public Reparacoes() {
        this.reparacaoMap = new HashMap<>();
        this.planoTrabalhoMap = new HashMap<>();
    }

    @Override
    public void createPlanosTrabalho(String idPedido) {
        PlanoTrabalho planoTrabalho = new PlanoTrabalho(idPedido);
        this.planoTrabalhoMap.put(idPedido, planoTrabalho);
    }

    public double getOrcamento (String idPlano){
        PlanoTrabalho planoTrabalho = planoTrabalhoMap.get(idPlano);
        return planoTrabalho.getOrcamento();
    }


    public void criaReparacao(String idReparacao, String idTecnico,double orcamento){
        Reparacao reparacao = new Reparacao(idReparacao,idTecnico,orcamento);
        this.reparacaoMap.put(idReparacao,reparacao);
    }

    @Override
    public void registaPasso(double horas, double custoPecas, String idReparacao) throws ValorSuperior {
        Reparacao reparacao = reparacaoMap.get(idReparacao);
        int indexPasso = reparacao.registaPasso(horas, custoPecas);
        PlanoTrabalho planoTrabalho = planoTrabalhoMap.get(idReparacao);
        double expectavel = planoTrabalho.getCustoPecasPasso(indexPasso);
        double diferenca = custoPecas-expectavel;
        reparacao.changeOrcamento(diferenca);
        if (reparacao.checkSuperior120()) throw new ValorSuperior();
    }

    @Override
    public void addPasso(String idPlano, double horas, double custoPecas,String descricao) throws InvalidIdException {
        if (planoTrabalhoMap.containsKey(idPlano))
            throw new InvalidIdException(idPlano, InvalidIdException.Type.PLANO_TRABALHO);
        PlanoTrabalho planoTrabalho = planoTrabalhoMap.get(idPlano);
        planoTrabalho.addPasso(horas,custoPecas,descricao);
    }

    public void addSubPasso(String idPlano,int indexPasso ,double horas, double custoPecas) {
        PlanoTrabalho planoTrabalho = planoTrabalhoMap.get(idPlano);
        planoTrabalho.addSubPasso(indexPasso,horas, custoPecas);
    }

    @Override
    public PlanoTrabalho getPlanoDeTrabalho(String idPedido) {
        PlanoTrabalho planoTrabalho = this.planoTrabalhoMap.get(idPedido);
        return planoTrabalho == null ? null : planoTrabalho.clone();
    }

    @Override
    public void reparacaoParaEspera(String idReparacao) throws InvalidIdException {
        if (!this.reparacaoMap.containsKey(idReparacao))
            throw new InvalidIdException(idReparacao, InvalidIdException.Type.REPARACAO);
        this.reparacaoMap.get(idReparacao).setEstado(Reparacao.Estado.PAUSA);
    }

    @Override
    public void registaConclusao(String idReparacao) throws InvalidIdException {
        if (!this.reparacaoMap.containsKey(idReparacao))
            throw new InvalidIdException(idReparacao, InvalidIdException.Type.REPARACAO);
        this.reparacaoMap.get(idReparacao).setEstado(Reparacao.Estado.FINALIZADA);
    }

    @Override
    public void conclusaoPlanoDeTrabalho(String idPedido){
        this.planoTrabalhoMap.get(idPedido).setEstado(PlanoTrabalho.Estado.AGUARDA_ACEITACAO);
    }


    public void planoTrabalhoParaEspera(String idPlano) throws InvalidIdException {
        if (this.planoTrabalhoMap.containsKey(idPlano))
            throw new InvalidIdException(idPlano, InvalidIdException.Type.PEDIDO);
        this.planoTrabalhoMap.get(idPlano).setEstado(PlanoTrabalho.Estado.PAUSA);
    }

    public Map.Entry<Double,Duration> getOrcamentoEHorasPlano(String idPlano){
        PlanoTrabalho planoTrabalho = planoTrabalhoMap.get(idPlano);
        double orcamento = planoTrabalho.getOrcamento();
        Duration duration = planoTrabalho.getDuracaoTotal();
        return new AbstractMap.SimpleEntry<>(orcamento,duration);
    }
    @Override
    public void reparacaoAguardaAceitacao(String idReparacao){
        Reparacao reparacao = reparacaoMap.get(idReparacao);
        reparacao.setEstado(Reparacao.Estado.AGURDA_ACEITACAO);
    }

    public List<String> listAguardaAceitacao () {
        List<String> resultList = new ArrayList<>();
        reparacaoMap.forEach((key, value) -> {
            if (value.aguardaAceitacao()) {
                resultList.add(key);
            }
        });
        return resultList;
    }

    public void reparacaoAceite(String idReparacao){
        Reparacao reparacao = reparacaoMap.get(idReparacao);
        reparacao.reparacaoAceite();
    }


    public Map<String,InfoReparacao> reparacoesByTecnicoMonth (List<String> reparacaoMonth){
        Map<String,InfoReparacao> resultMap = new TreeMap<>();
        for (String idReparacao : reparacaoMonth){
            Reparacao reparacao = reparacaoMap.get(idReparacao);
            PlanoTrabalho planoTrabalho = planoTrabalhoMap.get(idReparacao);
            Duration duracaoReal = reparacao.getDuracaoTotal();
            Duration duracaoPrevista = planoTrabalho.getDuracaoTotal();
            long desvio = duracaoReal.toHours() - duracaoPrevista.toHours();
            String idTecnico = reparacao.getIdTecnico();
            if (!resultMap.containsKey(idTecnico)){
                InfoReparacao infoReparacao = new InfoReparacao();
                resultMap.put(idTecnico,infoReparacao);
            }
            InfoReparacao infoReparacao = resultMap.get(idTecnico);
            infoReparacao.addInfo(duracaoReal,desvio);
        }
        return resultMap;
    }

    public void reparacoesExaustivaByTecnicoMonth (List<String> reparacaoMonth,Map<String,List<String>> resultMap){
        for (String idReparacao : reparacaoMonth){
            Reparacao reparacao = reparacaoMap.get(idReparacao);
            String idTecnico = reparacao.getIdTecnico();
            resultMap.putIfAbsent(idTecnico,new ArrayList<>());
            List<String> list = resultMap.get(idTecnico);
            reparacao.addDescricoesToList(list);
        }
    }




}
