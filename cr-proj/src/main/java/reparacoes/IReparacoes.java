package reparacoes;

import exceptions.InvalidIdException;

public interface IReparacoes {
    void createPlanosTrabalho(String idPedido);

    void registaPasso(double horas, double custoPecas, String idReparacao);

    void conclusaoPlanoDeTrabalho(String codPlanoDeTrabalho) throws InvalidIdException;

    PlanoTrabalho getPlanoDeTrabalho(String idPedido);

    void addPasso(String idPlano, double horas, double custoPecas) throws InvalidIdException;

    void reparacaoParaEspera(String idReparacao) throws InvalidIdException;

    void registaConclusao(String idReparacao) throws InvalidIdException;
}
