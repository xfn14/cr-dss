package reparacoes;

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
    public String createPlanosTrabalho() {
        return null;
    }

    @Override
    public String createPasso(float horas, float custoPecas) {
        return null;
    }

    @Override
    public void conclusaoPlanoDeTrabalho(String codPlanoDeTrabalho) {

    }

    @Override
    public String getPlanoDeTrabalho(String IdEquipamento) {
        return null;
    }

    @Override
    public void registaPasso(float horas, float custoPecas) {

    }

    @Override
    public void reparacaoParaEspera(String idReparacao) {

    }

    @Override
    public void registaConclusao(String idReparacao) {

    }
}
