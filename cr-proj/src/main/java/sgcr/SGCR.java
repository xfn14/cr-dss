package sgcr;

import emailHandler.Email;
import exceptions.*;
import pedidos.*;
import reparacoes.IReparacoes;
import reparacoes.InfoReparacao;
import reparacoes.PlanoTrabalho;
import reparacoes.Reparacoes;
import trabalhadores.ITrabalhadores;
import trabalhadores.Trabalhador;
import trabalhadores.Trabalhadores;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SGCR implements Serializable {
    private transient final Logger logger = Logger.getLogger("CR");
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
        pedidos.conclusaoReparacao(idReparacao);
        String idTecnico = reparacoes.getIdTecnico(idReparacao);
        trabalhadores.setAvailable(idTecnico);
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

        String idTecnico = reparacoes.getIdTecnico(idPlano);
        trabalhadores.setAvailable(idTecnico);

        reparacoes.getOrcamento(idPlano);
        double orcamento = reparacoes.getOrcamento(idPlano);
        this.pedidos.changeOrcamento(idPlano,orcamento);

        Duration duration = trabalhadores.getTrabalhoPorRealizarTecnico(idTecnico);

        Map.Entry<String, String> entryContactos = pedidos.getNomeEmailCliente(idPlano);
        String email = entryContactos.getValue();
        String nome = entryContactos.getKey();

        Email.pedidoOrcamento(email, nome, orcamento, duration);
    }



    public Trabalhador doLogin(String username, String passe) {
        return trabalhadores.doLogin(username, passe);
    }

    public boolean registarGestor(String id, String pass, String confirmaPass) throws JaExisteException, PasswordErradaException {
        return this.trabalhadores.registarGestor(id, pass, confirmaPass);
    }

    public boolean registarTecnico(String id, String pass, String confirmaPass) throws JaExisteException, PasswordErradaException {
        return this.trabalhadores.registarTecnico(id, pass, confirmaPass);
    }

    public boolean registarFuncionario(String id, String pass, String confirmaPass) throws JaExisteException, PasswordErradaException {
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

    public void criarFichaCliente(String nome, String email, String nmr, String nmrUtente)throws JaExisteException {
        this.pedidos.criarFichaCliente(nome, email, nmr, nmrUtente);
    }


    public void entregaEquipamento(String codPedido, String idFuncionario) {
        this.pedidos.entregaEquipamento(codPedido, idFuncionario);
        this.pedidos.finalizaPedido(codPedido);
    }

    public void criaPlanosTrabalho(String idPedido, String idTecnico) {
        this.pedidos.pedidoAtualizaIdPlano(idPedido);
        this.pedidos.pedidoADecorrer(idPedido);
        this.reparacoes.criaPlanosTrabalho(idPedido, idTecnico);
        this.trabalhadores.setNotAvailable(idTecnico);
    }

    public void iniciaReparacao(String idReparacao,String idTecnico) {
        this.pedidos.pedidoADecorrer(idReparacao);
        this.reparacoes.iniciaReparacao(idReparacao);
        this.trabalhadores.setNotAvailable(idTecnico);
    }

    public void registaPasso(double horas, double custoPecas, String descricao, String idReparacao) throws ValorSuperior {
        String idTecnico = reparacoes.getIdTecnico(idReparacao);
        try {
            this.reparacoes.registaPasso(horas, custoPecas, descricao, idReparacao);
            double horasExpectavel = reparacoes.getLastExpectavel(idReparacao);
            removeHorasTecnico(idTecnico,horasExpectavel);
        } catch (ValorSuperior e) {
            double horasExpectavel = reparacoes.getLastExpectavel(idReparacao);
            removeHorasTecnico(idTecnico,horasExpectavel);

            Map.Entry<String, String> entry = pedidos.getNomeEmailCliente(idReparacao);
            String email = entry.getValue();
            String nome = entry.getKey();
            this.reparacoes.reparacaoAguardaAceitacao(idReparacao);
            double orcamento = reparacoes.getOrcamento(idReparacao);
            Email.valorSuperiorOrcamento(email, nome,orcamento);
            throw new ValorSuperior();
        }

    }

    private void removeHorasTecnico(String idTecnico, double horas){
        trabalhadores.minusHorasTecnico(idTecnico, (long) horas);
    }

    public void addPasso(String idPlano, double horas, double custoPecas, String descricao) {
        try {
            this.reparacoes.addPasso(idPlano, horas, custoPecas, descricao);
            String idTecnico = reparacoes.getIdTecnico(idPlano);
            trabalhadores.addHorasTecnico(idTecnico, (long) horas);
        } catch (InvalidIdException e) {
            this.logger.log(Level.WARNING, "Id de tecnico invalido", e);
        }
    }

    public PlanoTrabalho getPlanoDeTrabalho(String idPedido) {
        return this.reparacoes.getPlanoDeTrabalho(idPedido);
    }

    public void reparacaoParaEsperaTempo(String idReparacao) {
        this.pedidos.pedidoParaPausa(idReparacao);
        this.reparacoes.reparacaoParaEsperaTempo(idReparacao);
        String idTecnico = reparacoes.getIdTecnico(idReparacao);
        trabalhadores.setAvailable(idTecnico);
    }
    public void reparacaoParaEsperaPecas(String idReparacao) {
        this.reparacoes.reparacaoParaEsperaPecas(idReparacao);
        String idTecnico = reparacoes.getIdTecnico(idReparacao);
        trabalhadores.setAvailable(idTecnico);
    }

    public Object[][] getListPedidoOrcamento(){
        List<Pedido> pedidos = this.pedidos.getPedidos().stream()
                .filter(p -> p instanceof PedidoOrcamento)
                .collect(Collectors.toList());
        Object[][] list = new Object[pedidos.size()][];
        int i = 0;
        for(Pedido p : pedidos){
            PedidoOrcamento pedido = (PedidoOrcamento) p;
            list[i++] = new Object[]{
                    pedido.getIdPedido(),
                    pedido.getIdCliente(),
                    pedido.getIdFuncionario(),
                    pedido.getDescricaoProblema(),
                    pedido.getOrcamento() == -1 ? "Nao Definido" : pedido.getOrcamento(),
                    pedido.getIdPlanoTrabalho() == null ? "Nao Criado" : pedido.getIdPlanoTrabalho(),
                    pedido.getEstado(),
                    this.pedidos.hasEquipamenteEntregue(pedido.getIdPedido())
            };
        }
        String[] header = new String[]{"ID", "ID_CLIENT", "ID_FUNCIONARIO", "DESCRIÇÃO", "ORÇAMENTO", "ID_PLANO_TRABALHO", "ESTADO", "ENTREGUE"};
        return list;
    }

    public Object[][] getListServicoExpresso(){
        List<Pedido> pedidos = this.pedidos.getPedidos().stream()
                .filter(p -> p instanceof ServicoExpresso)
                .collect(Collectors.toList());
        Object[][] list = new Object[pedidos.size()][];
        int i = 0;
        for(Pedido p : pedidos){
            ServicoExpresso pedido = (ServicoExpresso) p;
            list[i++] = new Object[]{
                    pedido.getIdPedido(),
                    pedido.getIdCliente(),
                    pedido.getIdFuncionario(),
                    pedido.getIdTecnico(),
                    pedido.getTipo(),
                    pedido.getDescricao(),
                    pedido.getEstado(),
                    this.pedidos.hasEquipamenteEntregue(pedido.getIdPedido())
            };
        }
        String[] header = new String[]{"ID", "ID_CLIENT", "ID_FUNCIONARIO", "ID_TECNICO", "TIPO_DE_SERVICO", "DESCRIÇÃO", "ESTADO", "ENTREGUE"};
        return list;
    }

    public Object[][] getListReparacoesByTecnico(LocalDateTime month) {
        List<String> pedidosMonth = pedidos.getPedidosConcluidosMonth(month);
        Map<String, Integer> servicosExpressoMap = pedidos.getNrServicosExpressoMonth(month, pedidosMonth);
        Map<String, InfoReparacao> infoReparacaoMap = reparacoes.reparacoesByTecnicoMonth(pedidosMonth);

        Map<String, Object[]> tabelaMap = new HashMap<>();

        for (Map.Entry<String, Integer> entry : servicosExpressoMap.entrySet()) {
            Object[] object = new Object[]{entry.getKey(), entry.getValue(), 0, 0};
            tabelaMap.put(entry.getKey(), object);
        }

        for (Map.Entry<String, InfoReparacao> entry : infoReparacaoMap.entrySet()) {
            String idTecnico= entry.getKey();
            InfoReparacao info = entry.getValue();
            int totalReparacoesProgramadas = info.getNumeroTotalReparacoes();
            long duracao = info.duracaoMedia();
            long desvio = info.mediaDesvio();
            Object[] object = new Object[]{idTecnico, totalReparacoesProgramadas,duracao,desvio};
            tabelaMap.putIfAbsent(idTecnico,object);

            Object[]objectTabela = tabelaMap.get(idTecnico);
            int totalReparacoesSE = (int) objectTabela[1];
            int numeroTotalReparacoes = totalReparacoesProgramadas + totalReparacoesSE;

            objectTabela[1] = numeroTotalReparacoes;
        }


//        String[] cabecalho = {"Id Tecnico", "Nr Reparacoes", "Duracao Media", "Desvio em relação à duração prevista"};
        return tabelaMap.values().toArray(Object[][]::new);


    }

    public Object[][]  getNrRececaoEntregaByFuncionario(LocalDateTime month) {
        Map<String, Integer> pedidosMap = pedidos.getNrPedidosByFuncionario(month);
        Map<String, Integer> entregasMap = pedidos.getNrEntregasByFuncionario(month);

        Map<String,Object[]> tabelaMap = new HashMap<>();


        for (Map.Entry<String,Integer> entry : pedidosMap.entrySet()){
            String idFuncionario = entry.getKey();
            int pedidosFeitos = entry.getValue();
            Object[] object = new Object[]{idFuncionario, pedidosFeitos,0};
            tabelaMap.putIfAbsent(idFuncionario,object);
        }

        for (Map.Entry<String,Integer> entry : entregasMap.entrySet()){
            String idFuncionario = entry.getKey();
            int entregasFeitas = entry.getValue();
            Object[] object = new Object[]{idFuncionario,0,entregasFeitas};
            tabelaMap.putIfAbsent(idFuncionario,object);
            object = tabelaMap.get(idFuncionario);
            object[2]= entregasFeitas;
        }


        //String[] cabecalho = {"Id Funcionario", "Pedidos Efetuados", "Entregas Efetuadas"};
        return tabelaMap.values().toArray(Object[][]::new);
    }

    public Object[][] getListIntervencoesByTecnico(LocalDateTime month) {
        List<String> pedidosMonth = pedidos.getPedidosConcluidosMonth(month);
        Map<String, List<String>> resultMap = pedidos.getServicosExpressoByTecnico(pedidosMonth);
        reparacoes.reparacoesExaustivaByTecnicoMonth(pedidosMonth, resultMap);


        List<Object[]> listObjects = new ArrayList<>();

        for (Map.Entry<String,List<String>> entry : resultMap.entrySet()){
            String idTecnico = entry.getKey();
            List<String> intervencoes = entry.getValue();
            for (String str : intervencoes){
                Object[] object = new Object[]{idTecnico,str};
                listObjects.add(object);
            }
        }

        //String[]cabecalho ={"Id Tecnico","Intervencao"};
        return listObjects.toArray(Object[][]::new);
    }

    public void registaAceitacaoPlanoCliente(String idPedido) {
        pedidos.registaAceitacaoCliente(idPedido);
        reparacoes.planoTrabalhoAceite(idPedido);
    }

    public void registaAceitacaoReparacaoCliente(String idReparacao) {
        reparacoes.reparacaoAceite(idReparacao);
    }

    public Map<String,Map.Entry<String,LocalDateTime>> listPedidosAguardaAceitacao() {
        return pedidos.aguardaResposta();
    }


    public Map<String,Map.Entry<String,LocalDateTime>> listReparacoesAguardaAceitacao() {
        List<String> list = reparacoes.listAguardaAceitacao();
        Map<String,Map.Entry<String,LocalDateTime>> resultMap = new HashMap<>();
        for (String id : list) {
            String email = pedidos.getNomeEmailCliente(id).getValue();
            LocalDateTime date = pedidos.getDataContactoValorSuperior(id);
            AbstractMap.SimpleEntry<String, LocalDateTime> entry = new AbstractMap.SimpleEntry<>(email, date);
            resultMap.put(id,entry);
        }
        return resultMap;
    }

    public void reparacaoParaDecorrer(String idReparacao) {
        reparacoes.reparacaoParaDecorrer(idReparacao);
    }

    public String getPedidoOrcamentoMaisAntigo() throws SemPedidosOrcamento {
        //TODO verificar se existe algum plano de trabalho do técnico em pausa

        return pedidos.getPedidoOrcamentoMaisAntigo();
    }

    public String getPedidoOrcamentoMaisAntigo(String idTecnico) throws SemPedidosOrcamento {
        try {
            return reparacoes.checkPlanoTrabalhoPausa(idTecnico);
        }catch (SemPlanoTrabalhoException e){
            return pedidos.getPedidoOrcamentoMaisAntigo();
        }
    }

    public String getReparacaoMaisUrgente(String idTecnico) throws SemReparacoesException {
        return reparacoes.getReparacaoMaisUrgente(idTecnico);
    }

    public void registarFormatarPC(String idCliente, String idFuncionario, String idTecnico, String descricao) {
        this.pedidos.registarFormatarPC(idCliente, idFuncionario, idTecnico, descricao);
        this.trabalhadores.setNotAvailable(idTecnico);
    }

    public void registarInstalarOS(String idCliente, String idFuncionario, String idTecnico, String descricao) {
        this.pedidos.registarInstalarOS(idCliente, idFuncionario, idTecnico, descricao);
        this.trabalhadores.setNotAvailable(idTecnico);
    }

    public void registarSubstituirEcra(String idCliente, String idFuncionario, String idTecnico, String descricao) {
        this.pedidos.registarSubstituirEcra(idCliente, idFuncionario, idTecnico, descricao);
        this.trabalhadores.setNotAvailable(idTecnico);
    }

    public void registarSubstituirBateria(String idCliente, String idFuncionario, String idTecnico, String descricao) {
        this.pedidos.registarSubstituirBateria(idCliente, idFuncionario, idTecnico, descricao);
        this.trabalhadores.setNotAvailable(idTecnico);
    }

    public void registarOutro(String idCliente, String idFuncionario, String idTecnico, String descricao) {
        this.pedidos.registarOutro(idCliente, idFuncionario, idTecnico, descricao);
        this.trabalhadores.setNotAvailable(idTecnico);
    }

    public boolean isClienteAutenticado(String idCliente) {
        return pedidos.isClienteAutenticado(idCliente);
    }

    public void addSubPasso(String idPlano, int indexPasso, double horas, double custoPecas, String descricao) {
        reparacoes.addSubPasso(idPlano, indexPasso, horas, custoPecas, descricao);
    }

    public String getDescricaoPedido(String idPedido) {
        return this.pedidos.getDescricaoPedido(idPedido);
    }

    public boolean isValidID(String idPedido){
        return this.pedidos.isValidPedidoID(idPedido);
    }

    public String getPassoAtualDescricao(String idReparacao){
        Map.Entry<String,String[]> entry = this.reparacoes.getPassoAtualDescricao(idReparacao);
        StringBuilder s = new StringBuilder(entry.getKey());
        if(entry.getValue() == null) return s.toString();
        s.append("\n");
        for(String sub : entry.getValue())
            s.append("    ").append(sub).append("\n");
        return s.toString();
    }

    public int getTotalPassos(String idReparacao){
        return this.reparacoes.getTotalPassos(idReparacao);
    }

    public int getPassoAtualIndex(String idReparacao){
        return this.reparacoes.getPassoAtualIndex(idReparacao);
    }

    public void arquivarPedido (String idPedido){
        pedidos.arquivarPedido(idPedido);
        reparacoes.arquivarPedido(idPedido);
    }

    public void terminaServicoExpresso (String idPedido) throws InvalidIdException {
        String idTecnico = pedidos.getIdTecnicoSE(idPedido);
        this.pedidos.finalizaPedido(idPedido);
        this.trabalhadores.setAvailable(idTecnico);
    }
}