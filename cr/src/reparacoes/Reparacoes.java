package reparacoes;

import exceptions.InvalidIdException;

import java.util.HashMap;
import java.util.Map;

public class Reparacoes implements IReparacoes {
    private Map<String,Reparacao> reparacaoMap;
    private Map<String,PlanoTrabalho> planoTrabalhoMap;  

    public Reparacoes(){
        this.reparacaoMap = new HashMap<>();
        this.planoTrabalhoMap = new HashMap<>();
    }

    @Override
    public void createPlanosTrabalho(String idPedido) {
        PlanoTrabalho planoTrabalho = new PlanoTrabalho(idPedido);
        this.planoTrabalhoMap.put(idPedido,planoTrabalho);
    }

    @Override
    public void registaPasso(double horas, double custoPecas){

    }

    @Override
    public void addPasso(String idPlano, double horas, double custoPecas) throws InvalidIdException {
        if (planoTrabalhoMap.containsKey(idPlano))
            throw new InvalidIdException(idPlano, InvalidIdException.Type.PLANO_TRABALHO);
        PlanoTrabalho planoTrabalho = planoTrabalhoMap.get(idPlano);
        Passo passo = new Passo(horas, custoPecas);
        planoTrabalho.addPasso(passo);
    }

    @Override
    public PlanoTrabalho getPlanoDeTrabalho(String idPedido){
        PlanoTrabalho planoTrabalho = this.planoTrabalhoMap.get(idPedido);
        return planoTrabalho == null ? null : planoTrabalho.clone();
    }

    @Override
    public void reparacaoParaEspera(String idReparacao) {
        if (this.reparacaoMap.containsKey(idReparacao)) {
            this.reparacaoMap.get(idReparacao).setEstado(Reparacao.Estado.PAUSA);
        }
    }

    @Override
    public void registaConclusao(String idReparacao) {
        if (this.reparacaoMap.containsKey(idReparacao)) {
            this.reparacaoMap.get(idReparacao).setEstado(Reparacao.Estado.FINALIZADA);
        }
    }

    @Override
    public void conclusaoPlanoDeTrabalho(String IdPedido) {
        if (this.planoTrabalhoMap.containsKey(IdPedido)){
            this.planoTrabalhoMap.get(IdPedido).setEstado(PlanoTrabalho.Estado.AGUARDA_ACEITACAO);
        }
    }



}
