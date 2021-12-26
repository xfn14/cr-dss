package reparacoes;

public interface IReparacoes {
    void createPlanosTrabalho(String idPedido);
    void registaPasso(double horas, double custoPecas);
    void conclusaoPlanoDeTrabalho(String codPlanoDeTrabalho);
    PlanoTrabalho getPlanoDeTrabalho(String IdEquipamento);
    void addPasso(double horas, double custoPecas,String idPlano);
    void reparacaoParaEspera(String idReparacao);
    void registaConclusao(String idReparacao);
}
