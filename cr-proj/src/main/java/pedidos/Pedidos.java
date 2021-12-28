package pedidos;

import exceptions.InvalidIdException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        List<String> ret = new ArrayList<>();
        for (Pedido p : this.pedidoMap.values())
            if (p.getEstado().equals(Pedido.Estado.FINALIZADO))
                ret.add(p.getIdPedido());
        return ret;
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
    public boolean verificarDisponibilidadeSE(String idServicoExpresso) throws InvalidIdException {
        Pedido p = this.pedidoMap.get(idServicoExpresso);
        if (!(p instanceof ServicoExpresso))
            throw new InvalidIdException(idServicoExpresso, InvalidIdException.Type.PEDIDO);
        return true;
    }

    @Override
    public void cancelaPedido(String idPedido) throws InvalidIdException {
        Pedido pedido = this.pedidoMap.get(idPedido);
        if (pedido == null)
            throw new InvalidIdException(idPedido, InvalidIdException.Type.PEDIDO);
        pedido.setEstado(Pedido.Estado.CANCELADO);
    }

    /*
    @Override
    public void registaPedidoOrcamento(String codPedido) throws InvalidIdException {
        Pedido pedido = this.pedidoMap.get(codPedido);
        if (pedido == null || pedido instanceof ServicoExpresso)
            throw new InvalidIdException(codPedido, InvalidIdException.Type.PEDIDO);
        PedidoOrcamento pedidoOrcamento = new PedidoOrcamento(pedido, -1, codPedido);
        this.pedidoMap.put(codPedido, pedidoOrcamento);
    }
     */

    public void registaPedidoOrcamento(String idCliente,String idFuncionario,String descricao){
        //TODO: Equipamento key
        String nrPedido = String.valueOf(pedidoMap.size());
        PedidoOrcamento pedidoOrcamento = new PedidoOrcamento(nrPedido,idCliente,nrPedido,idFuncionario,descricao);
        pedidoMap.put(nrPedido,pedidoOrcamento);

    }

    public void registarFormatarPC(ServicoExpresso.Tipo tipo, String idCliente, String idFuncionario) {
        registarSE(ServicoExpresso.Tipo.FORMATAR_PC,idCliente,idFuncionario);
    }

    public void registarInstalarOS(ServicoExpresso.Tipo tipo, String idCliente, String idFuncionario) {
        registarSE(ServicoExpresso.Tipo.INSTALAR_OS,idCliente,idFuncionario);
    }

    public void registarSubstituirEcra(ServicoExpresso.Tipo tipo, String idCliente, String idFuncionario) {
        registarSE(ServicoExpresso.Tipo.SUBSTITUIR_ECRA,idCliente,idFuncionario);
    }

    public void registarSubstituirBateria(ServicoExpresso.Tipo tipo, String idCliente, String idFuncionario) {
        registarSE(ServicoExpresso.Tipo.SUBSTITUIR_BATERIA,idCliente,idFuncionario);
    }


    private void registarSE(ServicoExpresso.Tipo tipo, String idCliente, String idFuncionario) {
        //TODO: Equipamento key
        String nrPedido = String.valueOf(pedidoMap.size());
        ServicoExpresso servicoExpresso = new ServicoExpresso(nrPedido,idCliente,nrPedido,idFuncionario,tipo);
        pedidoMap.put(nrPedido,servicoExpresso);
    }


    @Override
    public void registarContactoParaLevantar(String idPedido,String idFuncionario){
        registarContactoCliente(idPedido, Contacto.Type.PRONTO_LEVANTAR,idFuncionario);
    }

    @Override
    public void registarContactoSemReparacao(String idPedido,String idFuncionario){
        registarContactoCliente(idPedido, Contacto.Type.SEM_REPARACAO,idFuncionario);
    }
    @Override
    public void registarContactoValorSuperior(String idPedido,String idFuncionario){
        registarContactoCliente(idPedido, Contacto.Type.VALOR_SUPERIOR,idFuncionario);
    }
    @Override
    public void registarContactoPedidoOrcamento(String idPedido,String idFuncionario){
        registarContactoCliente(idPedido, Contacto.Type.PEDIDO_ORCAMENTO,idFuncionario);
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
    public void adicionarParaLevantar(String idPedido) {
        Pedido p = this.pedidoMap.get(idPedido);
        p.setEstado(Pedido.Estado.FINALIZADO); //NAO SEI SE ESTA BEM?????
    }

    @Override
    public void entregaEquipamento(String codPedido, String idFuncionario) {
        Entrega entrega = new Entrega(idFuncionario, codPedido);
        this.entregaMap.put(codPedido, entrega);
    }

    @Override
    public void imprimirComprovativo(String idPedido) {
        //Pedido p = this.pedidoMap.get(idPedido);
        //if (p.getEstado().equals(Pedido.Estado.FINALIZADO))

    }

    @Override
    public void criarFichaCliente(String nome, String email, String nmr, String nif) {
        this.clientesMap.put(nif, new Cliente(nome, email, nmr, nif));
    }

    @Override
    public Map.Entry<String, String> getNomeEmailCliente(String idPedido) {
        Pedido pedido = pedidoMap.get(idPedido);
        String idCliente = pedido.getIdCliente();
        Cliente cliente = clientesMap.get(idCliente);
        return Map.entry(cliente.getNome(), cliente.getEmail());
    }
    
    public boolean existCliente(String idCliente){
        return clientesMap.containsKey(idCliente);
    }


    public List<Map.Entry<String,String>> aguardaResposta (){
        List<Map.Entry<String,String >> resultList = new ArrayList<>();
        for (Pedido pedido: pedidoMap.values()){
            if (pedido.aguardaReparacao()){
                String idPedido = pedido.getIdPedido();
                String idCliente = pedido.getIdCliente();
                Cliente cliente = clientesMap.get(idCliente);
                String email = cliente.getEmail();
                resultList.add(Map.entry(idPedido,email));

            }
        }
        return resultList;
    }


    public void conclusaoPlanoTrabalho(String idPedido){
        Pedido pedido= pedidoMap.get(idPedido);
        pedido.setEstado(Pedido.Estado.AGUARDA_ACEITACAO);
    }

}
