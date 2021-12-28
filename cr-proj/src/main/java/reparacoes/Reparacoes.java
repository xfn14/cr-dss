package reparacoes;

import exceptions.InvalidIdException;

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

    @Override
    public void registaPasso(double horas, double custoPecas, String idReparacao) {
        Reparacao reparacao = reparacaoMap.get(idReparacao);
        reparacao.registaPasso(horas, custoPecas);
        //TODO: comparar se o dinheiro gasto for superior ao orcamento
    }

    @Override
    public void addPasso(String idPlano, double horas, double custoPecas) throws InvalidIdException {
        if (planoTrabalhoMap.containsKey(idPlano))
            throw new InvalidIdException(idPlano, InvalidIdException.Type.PLANO_TRABALHO);
        PlanoTrabalho planoTrabalho = planoTrabalhoMap.get(idPlano);
        Passo passo = new Passo(horas, custoPecas);
        planoTrabalho.addPasso(passo);
    }

    public void addSubPasso(String idPlano, double horas, double custoPecas) {
        PlanoTrabalho planoTrabalho = planoTrabalhoMap.get(idPlano);
        planoTrabalho.addSubPasso(horas, custoPecas);
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
}
