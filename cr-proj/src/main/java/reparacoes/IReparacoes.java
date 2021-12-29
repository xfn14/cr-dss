package reparacoes;

import exceptions.InvalidIdException;
import exceptions.SemReparacoesException;
import exceptions.ValorSuperior;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public interface IReparacoes {
    void criaPlanosTrabalho(String idPedido,String idTecnico);

    Map.Entry<String,Double> registaPasso(double horas, double custoPecas, String idReparacao) throws ValorSuperior;

    void conclusaoPlanoDeTrabalho(String codPlanoDeTrabalho);

    PlanoTrabalho getPlanoDeTrabalho(String idPedido);

    void addPasso(String idPlano, double horas, double custoPecas,String descricao) throws InvalidIdException;

    void reparacaoParaEspera(String idReparacao) ;

    void reparacaoParaDecorrer(String idReparacao);

    void registaConclusao(String idReparacao) throws InvalidIdException;

    double getOrcamento(String idPlano);

    Map.Entry<Double, String> getOrcamentoEHorasPlano(String idPlano);

    void reparacaoAguardaAceitacao(String idReparacao);

    List<String> listAguardaAceitacao();

    void reparacaoAceite(String idReparacao);

    Map<String,InfoReparacao> reparacoesByTecnicoMonth (List<String> reparacaoMonth);

    void reparacoesExaustivaByTecnicoMonth (List<String> reparacaoMonth,Map<String,List<String>> resultMap);

    String getIdTecnico (String idReparacao);

    void cancelaPedido (String idPedido);

    void iniciaReparacao (String idReparacao);

    void planoTrabalhoAceite(String idPlano);

    String getReparacaoMaisUrgente(String idTecnico) throws SemReparacoesException;

    void addSubPasso(String idPlano, int indexPasso, double horas, double custoPecas,String descricao);

    }
