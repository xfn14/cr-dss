package reparacoes;

import exceptions.InvalidIdException;
import exceptions.ValorSuperior;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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


    public void criaReparacao(String idReparacao, double orcamento){
        Reparacao reparacao = new Reparacao(idReparacao,orcamento);
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
    public void addPasso(String idPlano, double horas, double custoPecas) throws InvalidIdException {
        if (planoTrabalhoMap.containsKey(idPlano))
            throw new InvalidIdException(idPlano, InvalidIdException.Type.PLANO_TRABALHO);
        PlanoTrabalho planoTrabalho = planoTrabalhoMap.get(idPlano);
        Passo passo = new Passo(horas, custoPecas);
        planoTrabalho.addPasso(passo);
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
    public void conclusaoPlanoDeTrabalho(String idPedido) throws InvalidIdException {
        if (this.planoTrabalhoMap.containsKey(idPedido))
            throw new InvalidIdException(idPedido, InvalidIdException.Type.PEDIDO);
        this.planoTrabalhoMap.get(idPedido).setEstado(PlanoTrabalho.Estado.AGUARDA_ACEITACAO);
    }


    public void planoTrabalhoParaEspera(String idPlano) throws InvalidIdException {
        if (this.planoTrabalhoMap.containsKey(idPlano))
            throw new InvalidIdException(idPlano, InvalidIdException.Type.PEDIDO);
        this.planoTrabalhoMap.get(idPlano).setEstado(PlanoTrabalho.Estado.PAUSA);
    }

}
