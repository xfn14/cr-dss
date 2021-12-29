package sgcr;

import emailHandler.Email;
import exceptions.*;
import pedidos.IPedidos;
import pedidos.Pedidos;
import reparacoes.IReparacoes;
import reparacoes.Passo;
import reparacoes.PlanoTrabalho;
import reparacoes.Reparacoes;
import trabalhadores.ITrabalhadores;
import trabalhadores.Trabalhador;
import trabalhadores.Trabalhadores;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SGCR implements Serializable {
    private ITrabalhadores trabalhadores;
    private IPedidos pedidos;
    private IReparacoes reparacoes;

    public SGCR() {
        this.trabalhadores = new Trabalhadores();
        this.pedidos = new Pedidos();
        this.reparacoes = new Reparacoes();
    }

    public ITrabalhadores getTrabalhadores() {
        return this.trabalhadores;
    }

    public void setTrabalhadores(ITrabalhadores trabalhadores) {
        this.trabalhadores = trabalhadores;
    }

    public IPedidos getPedidos() {
        return this.pedidos;
    }

    public void setPedidos(IPedidos pedidos) {
        this.pedidos = pedidos;
    }

    public IReparacoes getReparacoes() {
        return this.reparacoes;
    }

    public void setReparacoes(IReparacoes reparacoes) {
        this.reparacoes = reparacoes;
    }

    public void registaConclusaoReparacao(String idReparacao) throws InvalidIdException {
        reparacoes.registaConclusao(idReparacao);
        pedidos.finalizaPedido(idReparacao);
        Map.Entry<String, String> entry = pedidos.getNomeEmailCliente(idReparacao);
        String email = entry.getValue();
        String nome = entry.getKey();
        //Informar o cliente que o equipamento está pronto a levantar
        Email.prontoALevantar(email, nome);
    }

    public void registaEquipamentoSemReparacao(String idPedido) throws InvalidIdException {
        pedidos.cancelaPedido(idPedido);
        reparacoes.cancelaPedido(idPedido);
        Map.Entry<String, String> entry = pedidos.getNomeEmailCliente(idPedido);
        String email = entry.getValue();
        String nome = entry.getKey();
        //Informar o cliente que o equipamento não tem reparação
        Email.naoPodeSerReparado(email, nome);
    }

    public void registaConclusaoPlanoTrabalho(String idPlano) {
        this.reparacoes.conclusaoPlanoDeTrabalho(idPlano);
        this.pedidos.pedidoAguardaAceitacao(idPlano);
        this.pedidos.registarContactoPedidoOrcamento(idPlano, "Sistema");

        Map.Entry<Double, String> entry = reparacoes.getOrcamentoEHorasPlano(idPlano);
        double orcamento = entry.getKey();
        String idTecnico = entry.getValue();

        Duration duration = trabalhadores.getTrabalhoPorRealizarTecnico(idTecnico);

        Map.Entry<String, String> entryContactos = pedidos.getNomeEmailCliente(idPlano);
        String email = entryContactos.getValue();
        String nome = entryContactos.getKey();

        Email.pedidoOrcamento(email, nome, orcamento, duration);
    }

    public Trabalhador doLogin(String username, String passe) {
        return trabalhadores.doLogin(username, passe);
    }

    public boolean registarGestor(String id, String pass, String confirmaPass) {
        return this.trabalhadores.registarGestor(id, pass, confirmaPass);
    }

    public boolean registarTecnico(String id, String pass, String confirmaPass) {
        return this.trabalhadores.registarTecnico(id, pass, confirmaPass);
    }

    public boolean registarFuncionario(String id, String pass, String confirmaPass) {
        return this.trabalhadores.registarFuncionario(id, pass, confirmaPass);
    }

    public List<String> getListTecnicos() {
        return trabalhadores.getListTecnicos();
    }

    public Trabalhador verificarDisponibilidadeTecnicos() throws SemTecnicosDisponiveis {
        return this.trabalhadores.verificarDisponibilidadeTecnicos();
    }

    public List<String> getListPedidosOrcamento() {
        return pedidos.getListPedidosOrcamento();
    }

    public List<String> getListEquipamentosLevantar() {
        return pedidos.getListEquipamentosLevantar();
    }

    public double getPrecoSE(String idServicoExpresso) throws InvalidIdException {
        return pedidos.getPrecoSE(idServicoExpresso);
    }

    public void cancelaPedido(String idPedido) throws InvalidIdException {
        this.pedidos.cancelaPedido(idPedido);
        this.reparacoes.cancelaPedido(idPedido);
    }

    public void registaPedidoOrcamento(String idCliente, String idFuncionario, String descricao) {
        this.pedidos.registaPedidoOrcamento(idCliente, idFuncionario, descricao);
    }

    public void criarFichaCliente(String nome, String email, String nmr, String nmrUtente) {
        this.pedidos.criarFichaCliente(nome, email, nmr, nmrUtente);
    }


    public void entregaEquipamento(String codPedido, String idFuncionario) {
        this.pedidos.entregaEquipamento(codPedido, idFuncionario);
    }

    public void criaPlanosTrabalho(String idPedido, String idTecnico) {
        this.pedidos.pedidoADecorrer(idPedido);
        this.reparacoes.criaPlanosTrabalho(idPedido, idTecnico);
        this.trabalhadores.setNotAvailable(idTecnico);

    }

    public void iniciaReparacao(String idReparacao, String idTecnico) {
        this.pedidos.pedidoADecorrer(idReparacao);
        this.reparacoes.iniciaReparacao(idReparacao);
        this.trabalhadores.setNotAvailable(idTecnico);
    }

    public void registaPasso(double horas, double custoPecas, String idReparacao) {
        Map.Entry<String, Double> entryPasso = null;
        try {
            entryPasso = this.reparacoes.registaPasso(horas, custoPecas, idReparacao);
        } catch (ValorSuperior e) {
            Map.Entry<String, String> entry = pedidos.getNomeEmailCliente(idReparacao);
            String email = entry.getValue();
            String nome = entry.getKey();
            this.reparacoes.reparacaoAguardaAceitacao(idReparacao);
            //this.pedidos.
            Email.valorSuperiorOrcamento(email, nome);
        }
        assert entryPasso != null;
        String idTecnico = entryPasso.getKey();
        double horasPlano = entryPasso.getValue();
        trabalhadores.minusHorasTecnico(idTecnico, (long) horasPlano);
    }

    public void addPasso(String idPlano, double horas, double custoPecas, String descricao) throws InvalidIdException {
        this.reparacoes.addPasso(idPlano, horas, custoPecas, descricao);
        String idTecnico = reparacoes.getIdTecnico(idPlano);
        trabalhadores.addHorasTecnico(idTecnico, (long) horas);
    }

    public PlanoTrabalho getPlanoDeTrabalho(String idPedido) {
        return this.reparacoes.getPlanoDeTrabalho(idPedido);
    }

    public void reparacaoParaEspera(String idReparacao) {
        this.reparacoes.reparacaoParaEspera(idReparacao);
    }

    public void conclusaoReparacao(String idReparacao) throws InvalidIdException {
        this.pedidos.finalizaPedido(idReparacao);
        this.reparacoes.registaConclusao(idReparacao);
        String idTecnico = reparacoes.getIdTecnico(idReparacao);
        trabalhadores.setAvailable(idTecnico);


    }

    public void conclusaoPlanoDeTrabalho(String idPedido) {
        this.pedidos.pedidoAguardaAceitacao(idPedido);
        this.reparacoes.conclusaoPlanoDeTrabalho(idPedido);
        String idTecnico = reparacoes.getIdTecnico(idPedido);
        trabalhadores.setAvailable(idTecnico);
    }

    public List<String> getListReparacoesByTecnico(LocalDateTime month) {
        List<String> pedidosMonth = pedidos.getPedidosConcluidosMonth(month);
        Map<String, Integer> servicosExpressoMap = pedidos.getNrServicosExpressoMonth(month, pedidosMonth);

        return null;
    }

    public Map<String, List<String>> getListIntervencoesByTecnico(LocalDateTime month) {
        List<String> pedidosMonth = pedidos.getPedidosConcluidosMonth(month);
        Map<String, List<String>> resultMap = pedidos.getServicosExpressoByTecnico(pedidosMonth);
        reparacoes.reparacoesExaustivaByTecnicoMonth(pedidosMonth, resultMap);
        return resultMap;
    }

    public int getNrRececaoEntregaByFuncionario(LocalDateTime month) {
        Map<String, Integer> pedidosMap = pedidos.getNrPedidosByFuncionario(month);
        Map<String, Integer> entregasMap = pedidos.getNrEntregasByFuncionario(month);
        return 0;
    }

    public void registaAceitacaoPlanoCliente(String idPedido) {
        pedidos.registaAceitacaoCliente(idPedido);
        reparacoes.planoTrabalhoAceite(idPedido);
    }

    public void registaAceitacaoReparacaoCliente(String idReparacao) {
        reparacoes.reparacaoAceite(idReparacao);
    }

    public List<Map.Entry<String, String>> listPedidosAguardaAceitacao() {
        return pedidos.aguardaResposta();
    }

    public List<Map.Entry<String, String>> listReparacoesAguardaAceitacao() {
        List<String> list = reparacoes.listAguardaAceitacao();
        List<Map.Entry<String, String>> resultList = new ArrayList<>();
        for (String id : list) {
            String email = pedidos.getNomeEmailCliente(id).getValue();
            AbstractMap.SimpleEntry<String, String> entry = new AbstractMap.SimpleEntry<>(id, email);
            resultList.add(entry);
        }
        return resultList;
    }


    public void reparacaoParaDecorrer(String idReparacao) {
        reparacoes.reparacaoParaDecorrer(idReparacao);
    }

    public String getPedidoOrcamentoMaisAntigo() throws SemPedidosOrcamento {
        return pedidos.getPedidoOrcamentoMaisAntigo();
    }

    public String getReparacaoMaisUrgente(String idTecnico)throws SemReparacoesException{
        return reparacoes.getReparacaoMaisUrgente(idTecnico);
    }

    public void registarFormatarPC(String idCliente, String idFuncionario, String idTecnico, String descricao) {
        this.pedidos.registarFormatarPC(idCliente, idFuncionario, idTecnico, descricao);
    }

    public void registarInstalarOS(String idCliente, String idFuncionario, String idTecnico, String descricao) {
        this.pedidos.registarInstalarOS(idCliente, idFuncionario, idTecnico, descricao);
    }

    public void registarSubstituirEcra(String idCliente, String idFuncionario, String idTecnico, String descricao) {
        this.pedidos.registarSubstituirEcra(idCliente, idFuncionario, idTecnico, descricao);
    }

    public void registarSubstituirBateria(String idCliente, String idFuncionario, String idTecnico, String descricao) {
        this.pedidos.registarSubstituirBateria(idCliente, idFuncionario, idTecnico, descricao);
    }

    public void registarOutro(String idCliente, String idFuncionario, String idTecnico, String descricao) {
        this.pedidos.registarOutro(idCliente, idFuncionario, idTecnico, descricao);
    }

    public boolean isClienteAutenticado(String idCliente){
        return pedidos.isClienteAutenticado(idCliente);
        //return true;
    }

}