package reparacoes;

public interface IReparacoes {
    String createPlanosTrabalho();
    String createPasso(float horas, float custoPecas);
    void conclusaoPlanoDeTrabalho(String codPlanoDeTrabalho);
    String getPlanoDeTrabalho(String IdEquipamento);
    void registaPasso(float horas, float custoPecas);
    void reparacaoParaEspera(String idReparacao);
    void registaConclusao(String idReparacao);
}
