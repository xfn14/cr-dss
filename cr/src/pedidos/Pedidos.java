package pedidos;

import exceptions.InvalidIdException;

import java.util.*;
import java.util.stream.Collectors;

public class Pedidos implements IPedidos {
    private Map<String,Pedido> pedidoMap;
    private Map<String,Entrega> entregaMap;

    public Pedidos(){
        this.pedidoMap = new HashMap<>();
        this.entregaMap = new HashMap<>();
    }

    public void addPedido(Pedido pedido){
        this.pedidoMap.put(pedido.getIdPedido(), pedido.clone());
    }

    public void addEntrega(Entrega entrega){
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
        return null;
    }

    @Override
    public double getPrecoSE(String idServicoExpresso) throws InvalidIdException {
        Pedido pedido = this.pedidoMap.get(idServicoExpresso);
        if(pedido == null)
            throw new InvalidIdException(idServicoExpresso, InvalidIdException.Type.PEDIDO);
        ServicoExpresso se = (ServicoExpresso) pedido;
        return se.getTipo().getPreco();
    }

    @Override
    public boolean verificarDisponibilidadeSE(int idServicoExpresso) {
        return false;
    }

    @Override
    public void cancelaPedido(String idPedido) throws InvalidIdException {
        Pedido pedido = this.pedidoMap.get(idPedido);
        if(pedido == null)
            throw new InvalidIdException(idPedido, InvalidIdException.Type.PEDIDO);
        pedido.setEstado(Pedido.Estado.CANCELADO);
    }

    @Override
    public void registaPedidoOrcamento(String codPedido) {

    }

    @Override
    public void registarContactoCliente(String idCliente, Date data) {

    }

    @Override
    public void registaAceitacaoCliente(String idReparacao) {

    }

    @Override
    public void registarSE(int idServicoExpresso, String idCliente) {

    }

    @Override
    public void atualizaDisponibilidadeSE(int idServicoExpresso) {

    }

    @Override
    public void adicionarParaLevantar(String idEquipamento) {

    }

    @Override
    public void entregaEquipamento(String codE) {

    }

    @Override
    public void notificaCliente(String IdCliente) {

    }

    @Override
    public void imprimirComprovativo(String cdPedido) {

    }

    @Override
    public void criarFichaCliente(String nome, String email, String nmr) {

    }
}
