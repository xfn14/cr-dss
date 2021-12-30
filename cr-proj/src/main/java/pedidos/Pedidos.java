package pedidos;

import exceptions.InvalidIdException;
import exceptions.JaExisteException;
import exceptions.SemPedidosOrcamento;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Pedidos implements IPedidos, Serializable {
    private Map<String, Pedido> pedidoMap;
    private Map<String, Entrega> entregaMap;
    private Map<String, Cliente> clientesMap;


    public Pedidos() {
        this.pedidoMap = new HashMap<>();
        this.entregaMap = new HashMap<>();
        this.clientesMap = new HashMap<>();
    }

    public List<Cliente> getClientes() {
        return new ArrayList<>(this.clientesMap.values());
    }

    public List<String> getClientesId() {
        return new ArrayList<>(this.clientesMap.values()).stream()
                .map(Cliente::getNmrUtente)
                .collect(Collectors.toList());
    }

    public void addPedido(Pedido pedido) {
        this.pedidoMap.put(pedido.getIdPedido(), pedido.clone());
    }

    public void addEntrega(Entrega entrega) {
        this.entregaMap.put(entrega.getIdPedido(), entrega.clone());
    }

    public List<Pedido> getPedidos() {
        return new ArrayList<>(this.pedidoMap.values());
    }

    public List<Entrega> getEntregas() {
        return new ArrayList<>(this.entregaMap.values());
    }

    @Override
    public List<String> getListPedidosOrcamento() {
        return this.getPedidos().stream()
                .filter(p -> p instanceof PedidoOrcamento)
                .map(Pedido::getIdPedido)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getListEquipamentosLevantar() {
        List<String> porLevantarList = new ArrayList<>();
        for (Pedido pedido : this.pedidoMap.values()) {
            if (pedido.isFinalizado() && !entregaMap.containsKey(pedido.getIdPedido()))
                porLevantarList.add(pedido.getIdEquipamento());

        }
        return porLevantarList;
    }

    @Override
    public double getPrecoSE(String idServicoExpresso) throws InvalidIdException {
        Pedido pedido = this.pedidoMap.get(idServicoExpresso);
        if (pedido == null)
            throw new InvalidIdException(idServicoExpresso, InvalidIdException.Type.PEDIDO);
        ServicoExpresso se = (ServicoExpresso) pedido;
        return se.getTipo().getPreco();
    }


    @Override
    public void cancelaPedido(String idPedido) throws InvalidIdException {
        Pedido pedido = this.pedidoMap.get(idPedido);
        if (pedido == null)
            throw new InvalidIdException(idPedido, InvalidIdException.Type.PEDIDO);
        pedido.setEstado(Pedido.Estado.CANCELADO);
        // TODO rever
    }


    private void registarSE(ServicoExpresso.Tipo tipo, String idCliente, String idFuncionario, String idTecnico, String descricao) {
        String nrPedido = getNovoPedidoNumber();
        ServicoExpresso servicoExpresso = new ServicoExpresso(nrPedido, idCliente, nrPedido, idFuncionario, tipo, idTecnico, descricao);
        pedidoMap.put(nrPedido, servicoExpresso);
        addPedidoCliente(idCliente, nrPedido);
    }

    public void registaPedidoOrcamento(String idCliente, String idFuncionario, String descricao) {
        String nrPedido = getNovoPedidoNumber();
        PedidoOrcamento pedidoOrcamento = new PedidoOrcamento(nrPedido, idCliente, idFuncionario, nrPedido, descricao);
        pedidoMap.put(nrPedido, pedidoOrcamento);
        addPedidoCliente(idCliente, nrPedido);
    }

    private String getNovoPedidoNumber() {
        return String.valueOf(pedidoMap.size());
    }


    private void addPedidoCliente(String idCliente, String idPedido) {
        Cliente cliente = clientesMap.get(idCliente);
        cliente.addPedido(idPedido);
    }

    public void registarFormatarPC(String idCliente, String idFuncionario, String idTecnico, String descricao) {
        this.registarSE(ServicoExpresso.Tipo.FORMATAR_PC, idCliente, idFuncionario, idTecnico, descricao);
    }

    public void registarInstalarOS(String idCliente, String idFuncionario, String idTecnico, String descricao) {
        this.registarSE(ServicoExpresso.Tipo.INSTALAR_OS, idCliente, idFuncionario, idTecnico, descricao);
    }

    public void registarSubstituirEcra(String idCliente, String idFuncionario, String idTecnico, String descricao) {
        this.registarSE(ServicoExpresso.Tipo.SUBSTITUIR_ECRA, idCliente, idFuncionario, idTecnico, descricao);
    }

    public void registarSubstituirBateria(String idCliente, String idFuncionario, String idTecnico, String descricao) {
        this.registarSE(ServicoExpresso.Tipo.SUBSTITUIR_BATERIA, idCliente, idFuncionario, idTecnico, descricao);
    }

    public void registarOutro(String idCliente, String idFuncionario, String idTecnico, String descricao) {
        this.registarSE(ServicoExpresso.Tipo.OUTRO, idCliente, idFuncionario, idTecnico, descricao);
    }


    @Override
    public void registarContactoParaLevantar(String idPedido, String idFuncionario) {
        registarContactoCliente(idPedido, Contacto.Type.PRONTO_LEVANTAR, idFuncionario);
    }

    @Override
    public void registarContactoSemReparacao(String idPedido, String idFuncionario) {
        registarContactoCliente(idPedido, Contacto.Type.SEM_REPARACAO, idFuncionario);
    }

    @Override
    public void registarContactoValorSuperior(String idPedido, String idFuncionario) {
        registarContactoCliente(idPedido, Contacto.Type.VALOR_SUPERIOR, idFuncionario);
    }

    @Override
    public void registarContactoPedidoOrcamento(String idPedido, String idFuncionario) {
        registarContactoCliente(idPedido, Contacto.Type.PEDIDO_ORCAMENTO, idFuncionario);
    }

    private void registarContactoCliente(String idPedido, Contacto.Type tipo, String idFuncionario) {
        Pedido pedido = pedidoMap.get(idPedido);
        pedido.registaContacto(tipo, idFuncionario);
    }

    @Override
    public void registaAceitacaoCliente(String idReparacao) {
        Pedido pedido = this.pedidoMap.get(idReparacao);
        pedido.setEstado(Pedido.Estado.AGUARDA_REPARACAO);
    }


    @Override
    public void finalizaPedido(String idPedido) {
        Pedido p = this.pedidoMap.get(idPedido);
        p.setEstado(Pedido.Estado.FINALIZADO);
    }

    @Override
    public void entregaEquipamento(String codPedido, String idFuncionario) {
        Entrega entrega = new Entrega(idFuncionario, codPedido);
        this.entregaMap.put(codPedido, entrega);
    }


    public void pedidoADecorrer(String idPedido) {
        Pedido pedido = pedidoMap.get(idPedido);
        pedido.setEstado(Pedido.Estado.DECORRER);
    }

    @Override
    public void criarFichaCliente(String nome, String email, String nmr, String nif) throws JaExisteException{
        if (this.clientesMap.containsKey(nif))
            throw new JaExisteException("Já existe uma ficha de cliente associada ao nif: " + nif);
        this.clientesMap.put(nif, new Cliente(nome, nif, nmr, email));
    }

    @Override
    public Map.Entry<String, String> getNomeEmailCliente(String idPedido) {
        Pedido pedido = pedidoMap.get(idPedido);
        String idCliente = pedido.getIdCliente();
        Cliente cliente = clientesMap.get(idCliente);
        return new AbstractMap.SimpleEntry<>(cliente.getNome(), cliente.getEmail());
    }

    public boolean existCliente(String idCliente) {
        return clientesMap.containsKey(idCliente);
    }

    public Map<String,Map.Entry<String,LocalDateTime>> aguardaResposta() {

        //IdPedido,{Email,Data do Contacto}
        Map<String,Map.Entry<String,LocalDateTime>> mapResult = new HashMap<>();
        for (Pedido pedido : pedidoMap.values()) {
            if (pedido.aguardaReparacao()) {
                String idPedido = pedido.getIdPedido();
                String idCliente = pedido.getIdCliente();
                Cliente cliente = clientesMap.get(idCliente);
                String email = cliente.getEmail();
                LocalDateTime time = pedido.getDataContactoPedidoOrcamento();
                AbstractMap.SimpleEntry<String, LocalDateTime> entry = new AbstractMap.SimpleEntry<>(email,time);
                mapResult.put(idPedido,entry);
            }
        }
        return mapResult;
    }

    public void pedidoAguardaAceitacao(String idPedido) {
        Pedido pedido = pedidoMap.get(idPedido);
        pedido.setEstado(Pedido.Estado.AGUARDA_ACEITACAO);
    }

    public Map<String, Integer> getNrPedidosByFuncionario(LocalDateTime month) {

        Map<String, Integer> pedidosResult = new TreeMap<>();
        for (Pedido pedido : pedidoMap.values()) {
            LocalDateTime date = pedido.getData();
            if (date.getYear() == month.getYear() && date.getMonth().equals(month.getMonth())) {
                String funcionario = pedido.getIdFuncionario();
                pedidosResult.putIfAbsent(funcionario, 0);
                int value = pedidosResult.get(funcionario) + 1;
                pedidosResult.put(funcionario, value);
            }
        }
        return pedidosResult;
    }

    public Map<String, Integer> getNrEntregasByFuncionario(LocalDateTime month) {
        Map<String, Integer> entregasResult = new TreeMap<>();
        for (Entrega entrega : entregaMap.values()) {
            LocalDateTime date = entrega.getData();
            if (date.getYear() == month.getYear() && date.getMonth().equals(month.getMonth())) {
                String funcionario = entrega.getIdFuncionario();
                entregasResult.putIfAbsent(funcionario, 0);
                int value = 1 + entregasResult.get(funcionario);
                entregasResult.put(funcionario, value);
            }
        }
        return entregasResult;
    }

    public List<String> getPedidosConcluidosMonth(LocalDateTime month) {
        return pedidoMap.values().stream().
                filter(pedido -> pedido.getData().getMonth().equals(month.getMonth()) && pedido.getData().getYear() == month.getYear()).
                filter(pedido -> pedido.getEstado().equals(Pedido.Estado.FINALIZADO)).
                map(Pedido::getIdPedido).collect(Collectors.toList());
    }

    public Map<String, Integer> getNrServicosExpressoMonth(LocalDateTime month, List<String> pedidos) {
        Map<String, Integer> resultMap = new TreeMap<>();
        //iterating backwards to remove element
        for (int i = pedidos.size() - 1; i >= 0; i--) {
            String idPedido = pedidos.get(i);
            Pedido pedido = pedidoMap.get(idPedido);
            if (pedido instanceof ServicoExpresso) {
                String tecnico = ((ServicoExpresso) pedido).getIdTecnico();
                resultMap.putIfAbsent(tecnico, 0);
                int value = 1 + resultMap.get(tecnico);
                resultMap.put(tecnico, value);
                pedidos.remove(i);
            }
        }
        return resultMap;
    }

    public Map<String, List<String>> getServicosExpressoByTecnico(List<String> pedidos) {
        Map<String, List<String>> resultMap = new TreeMap<>();
        //iterating backwards to remove element
        for (int i = pedidos.size() - 1; i >= 0; i--) {
            String idPedido = pedidos.get(i);
            Pedido pedido = pedidoMap.get(idPedido);
            if (pedido instanceof ServicoExpresso) {
                String tecnico = ((ServicoExpresso) pedido).getIdTecnico();
                resultMap.putIfAbsent(tecnico, new ArrayList<>());
                List<String> list = resultMap.get(tecnico);
                String tipoSE = ((ServicoExpresso) pedido).getTipo().toString();
                list.add(tipoSE);
                pedidos.remove(i);
            }
        }
        return resultMap;
    }

    public String getPedidoOrcamentoMaisAntigo() throws SemPedidosOrcamento {
        List<Pedido> pedidosOrcamento = pedidoMap.values().stream().
                filter(pedido -> pedido instanceof PedidoOrcamento && pedido.aguardaPlano()).
                collect(Collectors.toList());
        List<Pedido> pedidosOrcamentoSorte = pedidosOrcamento.stream().sorted(Comparator.comparing(Pedido::getData)).collect(Collectors.toList());
        if (pedidosOrcamento.size() == 0) throw new SemPedidosOrcamento();
        Pedido maisAntigo = pedidosOrcamentoSorte.get(pedidosOrcamento.size() - 1);
        return maisAntigo.getIdPedido();
    }

    public boolean isClienteAutenticado(String idCliente) {
        return clientesMap.containsKey(idCliente);
    }

    public String getDescricaoPedido(String idPedido) {
        Pedido p = this.pedidoMap.get(idPedido);
        if (p instanceof PedidoOrcamento) {
            return ((PedidoOrcamento) p).getDescricaoProblema();
        }
        return "";
    }

    public boolean isValidPedidoID(String idPedido) {
        return this.pedidoMap.containsKey(idPedido);
    }

    public boolean hasEquipamenteEntregue(String idPedido) {
        return this.entregaMap.containsKey(idPedido);
    }

    public LocalDateTime getDataContactoValorSuperior(String id){
        Pedido pedido = pedidoMap.get(id);
        return pedido.getDataContactoValorSuperior();
    }

    public void arquivarPedido(String idPedido){
        Pedido pedido = pedidoMap.get(idPedido);
        pedido.setEstado(Pedido.Estado.ARQUIVADO);
    }

    public void pedidoAtualizaIdPlano(String idPedido){
        Pedido pedido = pedidoMap.get(idPedido);
        if (pedido instanceof PedidoOrcamento) ((PedidoOrcamento) pedido).setIdPlanoTrabalho(idPedido);
    }

    public String getIdTecnicoSE(String idPedido) throws InvalidIdException{
        Pedido pedido = pedidoMap.get(idPedido);
        if (pedido instanceof ServicoExpresso) return ((ServicoExpresso) pedido).getIdTecnico();
        throw new InvalidIdException(idPedido, InvalidIdException.Type.SERVICO_EXPRESSO);
    }

    public void pedidoParaPausa (String idPedido){
        Pedido pedido = pedidoMap.get(idPedido);
        pedido.setEstado(Pedido.Estado.PAUSA);
    }

    public void changeOrcamento(String idPlano,double orcamento) {
        Pedido pedido = pedidoMap.get(idPlano);
        if (pedido instanceof PedidoOrcamento) ((PedidoOrcamento) pedido).setOrcamento(orcamento);
    }

    public void conclusaoReparacao (String idPlano){
        Pedido pedido = pedidoMap.get(idPlano);
        pedido.setEstado(Pedido.Estado.AGUARDA_ENTREGA);
    }
}
