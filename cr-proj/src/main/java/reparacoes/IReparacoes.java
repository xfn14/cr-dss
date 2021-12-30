package reparacoes;

import exceptions.InvalidIdException;
import exceptions.SemPlanoTrabalhoException;
import exceptions.SemReparacoesException;
import exceptions.ValorSuperior;

import java.util.List;
import java.util.Map;

public interface IReparacoes {
    void criaPlanosTrabalho(String idPedido, String idTecnico);

    void registaPasso(double horas, double custoPecas, String descricao, String idReparacao) throws ValorSuperior;

    void conclusaoPlanoDeTrabalho(String codPlanoDeTrabalho);

    PlanoTrabalho getPlanoDeTrabalho(String idPedido);

    void addPasso(String idPlano, double horas, double custoPecas, String descricao) throws InvalidIdException;

    void reparacaoParaEsperaTempo(String idReparacao);

    void reparacaoParaEsperaPecas(String idReparacao);

    void reparacaoParaDecorrer(String idReparacao);

    void registaConclusao(String idReparacao) throws InvalidIdException;

    double getOrcamento(String idPlano);

    void reparacaoAguardaAceitacao(String idReparacao);

    List<String> listAguardaAceitacao();

    void reparacaoAceite(String idReparacao);

    Map<String, InfoReparacao> reparacoesByTecnicoMonth(List<String> reparacaoMonth);

    void reparacoesExaustivaByTecnicoMonth(List<String> reparacaoMonth, Map<String, List<String>> resultMap);

    String getIdTecnico(String idReparacao);

    void cancelaPedido(String idPedido);

    void iniciaReparacao(String idReparacao);

    void planoTrabalhoAceite(String idPlano);

    String getReparacaoMaisUrgente(String idTecnico) throws SemReparacoesException;

    void addSubPasso(String idPlano, int indexPasso, double horas, double custoPecas, String descricao);

    Map.Entry<String, String[]> getPassoAtualDescricao(String idReparacao);

    int getTotalPassos(String idReparacao);

    int getPassoAtualIndex(String idReparacao);

    String checkPlanoTrabalhoPausa (String idTecnico) throws SemPlanoTrabalhoException;

    void arquivarPedido(String idPedido);

    double getLastExpectavel(String idPlano);

    }
