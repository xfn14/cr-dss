package reparacoes;

import exceptions.InvalidIdException;

public interface IReparacoes {
    void createPlanosTrabalho(String idPedido);
    void registaPasso(double horas, double custoPecas);
    void conclusaoPlanoDeTrabalho(String codPlanoDeTrabalho);
    PlanoTrabalho getPlanoDeTrabalho(String idPedido);
    void addPasso(String idPlano, double horas, double custoPecas) throws InvalidIdException;
    void reparacaoParaEspera(String idReparacao);
    void registaConclusao(String idReparacao);
}
