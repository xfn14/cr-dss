package reparacoes;

import exceptions.InvalidIdException;
import exceptions.ValorSuperior;

public interface IReparacoes {
    void createPlanosTrabalho(String idPedido);

    void registaPasso(double horas, double custoPecas, String idReparacao) throws ValorSuperior;

    void conclusaoPlanoDeTrabalho(String codPlanoDeTrabalho) throws InvalidIdException;

    PlanoTrabalho getPlanoDeTrabalho(String idPedido);

    void addPasso(String idPlano, double horas, double custoPecas) throws InvalidIdException;

    void reparacaoParaEspera(String idReparacao) throws InvalidIdException;

    void registaConclusao(String idReparacao) throws InvalidIdException;

    double getOrcamento (String idPlano);

}
