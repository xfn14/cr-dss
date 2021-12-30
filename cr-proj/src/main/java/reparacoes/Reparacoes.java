package reparacoes;

import exceptions.InvalidIdException;
import exceptions.SemPlanoTrabalhoException;
import exceptions.SemReparacoesException;
import exceptions.ValorSuperior;
import pedidos.Pedido;

import java.io.Serializable;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class Reparacoes implements IReparacoes, Serializable {
    private final Map<String, Reparacao> reparacaoMap;
    private final Map<String, PlanoTrabalho> planoTrabalhoMap;
    private final Map<String, List<String>> queueMap;

    public Reparacoes() {
        this.reparacaoMap = new HashMap<>();
        this.planoTrabalhoMap = new HashMap<>();
        this.queueMap = new HashMap<>();
    }

    @Override
    public void criaPlanosTrabalho(String idPedido, String idTecnico) {
        PlanoTrabalho planoTrabalho = new PlanoTrabalho(idPedido, idTecnico);
        this.planoTrabalhoMap.put(idPedido, planoTrabalho);
    }

    public void iniciaReparacao(String idReparacao) {
        Reparacao reparacao = reparacaoMap.get(idReparacao);
        reparacao.setEstado(Reparacao.Estado.DECORRER);
    }

    private void criaReparacao(String idReparacao, String idTecnico, double orcamento) {
        Reparacao reparacao = new Reparacao(idReparacao, idTecnico, orcamento);
        this.reparacaoMap.put(idReparacao, reparacao);
    }

    public double getOrcamento(String idPlano) {
        PlanoTrabalho planoTrabalho = planoTrabalhoMap.get(idPlano);
        return planoTrabalho.getOrcamento();
    }

    public int getTotalPassos(String idReparacao){
        return this.planoTrabalhoMap.get(idReparacao).getPassos().size();
    }

    public int getPassoAtualIndex(String idReparacao){
        return this.reparacaoMap.get(idReparacao).passoAtual();
    }

    public Map.Entry<String, String[]> getPassoAtualDescricao(String idReparacao) {
        Reparacao reparacao = reparacaoMap.get(idReparacao);
        PlanoTrabalho planoTrabalho = this.planoTrabalhoMap.get(idReparacao);
        return planoTrabalho.passoToEntry(reparacao.passoAtual());
    }

    @Override
    public void registaPasso(double horas, double custoPecas,String descricao ,String idReparacao) throws ValorSuperior {
        Reparacao reparacao = reparacaoMap.get(idReparacao);
        reparacao.registaPasso(horas, custoPecas,descricao);
        int indexPasso = reparacao.passoAtual();
        PlanoTrabalho planoTrabalho = planoTrabalhoMap.get(idReparacao);
        double expectavel = planoTrabalho.getCustoPecasPasso(indexPasso);
        double diferenca = custoPecas - expectavel;
        reparacao.changeOrcamento(diferenca);
        if (reparacao.checkSuperior120()) throw new ValorSuperior();
    }

    public double getLastExpectavel(String idPlano){
        Reparacao reparacao = reparacaoMap.get(idPlano);
        int index = reparacao.passoAtual();
        PlanoTrabalho planoTrabalho = planoTrabalhoMap.get(idPlano);
        planoTrabalho.getCustoPecasPasso(index);
        return planoTrabalho.getCustoPecasPasso(index);
    }

    @Override
    public void addPasso(String idPlano, double horas, double custoPecas, String descricao) throws InvalidIdException {
        if (!planoTrabalhoMap.containsKey(idPlano))
            throw new InvalidIdException(idPlano, InvalidIdException.Type.PLANO_TRABALHO);
        PlanoTrabalho planoTrabalho = this.planoTrabalhoMap.get(idPlano);
        planoTrabalho.addPasso(horas, custoPecas, descricao);
    }

    public void addSubPasso(String idPlano, int indexPasso, double horas, double custoPecas, String descricao) {
        PlanoTrabalho planoTrabalho = this.planoTrabalhoMap.get(idPlano);
        planoTrabalho.addSubPasso(indexPasso, horas, custoPecas, descricao);
    }

    @Override
    public PlanoTrabalho getPlanoDeTrabalho(String idPedido) {
        PlanoTrabalho planoTrabalho = this.planoTrabalhoMap.get(idPedido);
        return planoTrabalho == null ? null : planoTrabalho.clone();
    }

    @Override
    public void registaConclusao(String idReparacao) {
        Reparacao reparacao = this.reparacaoMap.get(idReparacao);
        reparacao.setEstado(Reparacao.Estado.FINALIZADA);
        String idTecnico = reparacao.getIdTecnico();
        List<String> queueTecnico = queueMap.get(idTecnico);
        queueTecnico.remove(idReparacao);
    }

    @Override
    public void conclusaoPlanoDeTrabalho(String idPedido) {
        PlanoTrabalho planoTrabalho = this.planoTrabalhoMap.get(idPedido);
        planoTrabalho.setEstado(PlanoTrabalho.Estado.AGUARDA_ACEITACAO);
    }


    public void planoTrabalhoParaEspera(String idPlano) throws InvalidIdException {
        if (!this.planoTrabalhoMap.containsKey(idPlano))
            throw new InvalidIdException(idPlano, InvalidIdException.Type.PEDIDO);
        this.planoTrabalhoMap.get(idPlano).setEstado(PlanoTrabalho.Estado.PAUSA);
    }

    @Override
    public void reparacaoAguardaAceitacao(String idReparacao) {
        Reparacao reparacao = reparacaoMap.get(idReparacao);
        reparacao.setEstado(Reparacao.Estado.AGUARDA_ACEITACAO);
    }

    public List<String> listAguardaAceitacao() {
        List<String> resultList = new ArrayList<>();
        reparacaoMap.forEach((key, value) -> {
            if (value.aguardaAceitacao()) {
                resultList.add(key);
            }
        });
        return resultList;
    }

    public void reparacaoAceite(String idReparacao) {
        Reparacao reparacao = reparacaoMap.get(idReparacao);
        reparacao.reparacaoAceite();
    }

    public void planoTrabalhoAceite(String idPlano) {
        PlanoTrabalho planoTrabalho = planoTrabalhoMap.get(idPlano);
        planoTrabalho.setEstado(PlanoTrabalho.Estado.FINALIZADO);
        String idTecnico = planoTrabalho.getIdTecnico();
        double orcamento = planoTrabalho.getOrcamento();
        criaReparacao(idPlano, idTecnico, orcamento);
        queueMap.putIfAbsent(idTecnico, new ArrayList<>());
        List<String> queueTecnico = queueMap.get(idTecnico);
        queueTecnico.add(idPlano);
    }


    public Map<String, InfoReparacao> reparacoesByTecnicoMonth(List<String> reparacaoMonth) {
        Map<String, InfoReparacao> resultMap = new TreeMap<>();
        for (String idReparacao : reparacaoMonth) {
            Reparacao reparacao = reparacaoMap.get(idReparacao);
            PlanoTrabalho planoTrabalho = planoTrabalhoMap.get(idReparacao);
            Duration duracaoReal = reparacao.getDuracaoTotal();
            Duration duracaoPrevista = planoTrabalho.getDuracaoTotal();
            long desvio = duracaoReal.toHours() - duracaoPrevista.toHours();
            String idTecnico = reparacao.getIdTecnico();
            if (!resultMap.containsKey(idTecnico)) {
                InfoReparacao infoReparacao = new InfoReparacao();
                resultMap.put(idTecnico, infoReparacao);
            }
            InfoReparacao infoReparacao = resultMap.get(idTecnico);
            infoReparacao.addInfo(duracaoReal, desvio);
        }
        return resultMap;
    }

    public void reparacoesExaustivaByTecnicoMonth(List<String> reparacaoMonth, Map<String, List<String>> resultMap) {
        for (String idReparacao : reparacaoMonth) {
            Reparacao reparacao = reparacaoMap.get(idReparacao);
            String idTecnico = reparacao.getIdTecnico();
            resultMap.putIfAbsent(idTecnico, new ArrayList<>());
            List<String> list = resultMap.get(idTecnico);
            reparacao.addDescricoesToList(list);
        }
    }


    public void reparacaoParaDecorrer(String idReparacao) {
        reparacaoMap.get(idReparacao).setEstado(Reparacao.Estado.DECORRER);
    }

    public void reparacaoParaEsperaTempo(String idReparacao) {
        this.reparacaoMap.get(idReparacao).setEstado(Reparacao.Estado.PAUSA_TEMPO);
        System.out.println(this.reparacaoMap.get(idReparacao).getEstado());
    }

    public void reparacaoParaEsperaPecas(String idReparacao) {
        this.reparacaoMap.get(idReparacao).setEstado(Reparacao.Estado.PAUSA_PECAS);
    }

    public String getIdTecnico(String idReparacao) {
        return planoTrabalhoMap.get(idReparacao).getIdTecnico();
    }

    public void cancelaPedido(String idPedido) {
        if (planoTrabalhoMap.containsKey(idPedido)) {
            PlanoTrabalho planoTrabalho = planoTrabalhoMap.get(idPedido);
            planoTrabalho.setEstado(PlanoTrabalho.Estado.CANCELADO);
        }
        if (reparacaoMap.containsKey(idPedido)) {
            Reparacao reparacao = reparacaoMap.get(idPedido);
            reparacao.setEstado(Reparacao.Estado.CANCELADA);
        }
        String idTecnico = getIdTecnico(idPedido);
        List<String> queueList = queueMap.get(idTecnico);
        queueList.remove(idPedido);
    }

    public String getReparacaoMaisUrgente(String idTecnico) throws SemReparacoesException {
        if (!queueMap.containsKey(idTecnico)) throw new SemReparacoesException();
        List<String> queueTecnico = queueMap.get(idTecnico);
        for (String idPedido : queueTecnico) {
            Reparacao reparacao = reparacaoMap.get(idPedido);
            // TODO em pausa ta quando o tecnico tbm n esta a trabalhar nela ou quando mete em pausa
            // quando volta a abrir volta a meter em decorrer
            if (reparacao.getEstado().equals(Reparacao.Estado.PAUSA_PECAS)) continue;
            if (reparacao.aguardaAceitacao()) continue;
            if (reparacao.getEstado().equals(Reparacao.Estado.CANCELADA)) continue;
            reparacaoParaDecorrer(idPedido);
            return idPedido;
        }
        throw new SemReparacoesException();
    }

    public String checkPlanoTrabalhoPausa (String idTecnico) throws SemPlanoTrabalhoException {
        return planoTrabalhoMap.values().stream().
                filter(planoTrabalho -> planoTrabalho.getIdTecnico().equals(idTecnico) && planoTrabalho.getEstado().equals(PlanoTrabalho.Estado.PAUSA)).
                map(PlanoTrabalho::getIdPlanoTrabalho).
                findFirst().orElseThrow(SemPlanoTrabalhoException::new);
    }


    public void arquivarPedido(String idPedido){
        if (reparacaoMap.containsKey(idPedido)){
            Reparacao reparacao = reparacaoMap.get(idPedido);
            reparacao.setEstado(Reparacao.Estado.CANCELADA);
        }
        if (planoTrabalhoMap.containsKey(idPedido)){
            PlanoTrabalho planoTrabalho =planoTrabalhoMap.get(idPedido);
            planoTrabalho.setEstado(PlanoTrabalho.Estado.CANCELADO);
        }
    }




}
