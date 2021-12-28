package reparacoes;

import exceptions.InvalidIdException;
import exceptions.ValorSuperior;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public interface IReparacoes {
    void createPlanosTrabalho(String idPedido);

    void registaPasso(double horas, double custoPecas, String idReparacao) throws ValorSuperior;

    void conclusaoPlanoDeTrabalho(String codPlanoDeTrabalho);

    PlanoTrabalho getPlanoDeTrabalho(String idPedido);

    void addPasso(String idPlano, double horas, double custoPecas,String descricao) throws InvalidIdException;

    void reparacaoParaEspera(String idReparacao) throws InvalidIdException;

    void registaConclusao(String idReparacao) throws InvalidIdException;

    double getOrcamento(String idPlano);

    void criaReparacao(String idReparacao, String idTecnico,double orcamento);

    Map.Entry<Double, Duration> getOrcamentoEHorasPlano(String idPlano);

    void reparacaoAguardaAceitacao(String idReparacao);

    List<String> listAguardaAceitacao();

    void reparacaoAceite(String idReparacao);

    Map<String,InfoReparacao> reparacoesByTecnicoMonth (List<String> reparacaoMonth);


    public void reparacoesExaustivaByTecnicoMonth (List<String> reparacaoMonth,Map<String,List<String>> resultMap);

    }
