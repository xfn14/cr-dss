



package reparacoes;

import exceptions.InvalidIdException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.Time;

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
    public void registaPasso(double horas, double custoPecas) {

    }

    @Override
    public void addPasso(double horas, double custoPecas,String idPlano) {
        if (planoTrabalhoMap.containsKey(idPlano)); //return error later
        PlanoTrabalho planoTrabalho = planoTrabalhoMap.get(idPlano);
        Passo passo = new Passo(horas,custoPecas);
        planoTrabalho.addPasso(passo);
    }

    @Override
    public PlanoTrabalho getPlanoDeTrabalho(String IdPedido){
        if (this.planoTrabalhoMap.containsKey(IdPedido)){
            return this.planoTrabalhoMap.get(IdPedido).clone();
        }
        else return null;
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
            this.planoTrabalhoMap.get(IdPedido).setEstado(Reparacao.Estado.FINALIZADA);
        }
    }
}
