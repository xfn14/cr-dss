package pedidos;

import exceptions.InvalidIdException;

import java.util.*;
import java.util.stream.Collectors;

public class Pedidos implements IPedidos {
    private Map<String,Pedido> pedidoMap;
    private Map<String,Entrega> entregaMap;
    private Map<String, Cliente> clientesMap;

    public Pedidos(){
        this.pedidoMap = new HashMap<>();
        this.entregaMap = new HashMap<>();
        this.clientesMap = new HashMap<>();
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
        List<String> ret = new ArrayList<>();
        for (Pedido p : this.pedidoMap.values()){
            if(p.getEstado().equals(Pedido.Estado.FINALIZADO))
                ret.add(p.getIdPedido());
        }
        return ret;
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
    public boolean verificarDisponibilidadeSE(String idServicoExpresso) throws InvalidIdException {
        Pedido p = this.pedidoMap.get(idServicoExpresso);
        if (!(p instanceof ServicoExpresso))
            throw new InvalidIdException(idServicoExpresso, InvalidIdException.Type.PEDIDO);
        return true;
    }

    @Override
    public void cancelaPedido(String idPedido) throws InvalidIdException {
        Pedido pedido = this.pedidoMap.get(idPedido);
        if(pedido == null)
            throw new InvalidIdException(idPedido, InvalidIdException.Type.PEDIDO);
        pedido.setEstado(Pedido.Estado.CANCELADO);
    }

    @Override
    public void registaPedidoOrcamento(String codPedido) throws InvalidIdException{
        Pedido pedido = this.pedidoMap.get(codPedido);
        if(pedido == null || pedido instanceof ServicoExpresso)
            throw new InvalidIdException(codPedido, InvalidIdException.Type.PEDIDO);
        PedidoOrcamento pedidoOrcamento = new PedidoOrcamento(pedido,-1,codPedido);
        this.pedidoMap.put(codPedido,pedidoOrcamento);
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
    public void adicionarParaLevantar(String idPedido) {
        Pedido p = this.pedidoMap.get(idPedido);
        p.setEstado(Pedido.Estado.FINALIZADO); //NAO SEI SE ESTA BEM?????
    }

    @Override
    public void entregaEquipamento(String codPedido,String idFuncionario) {
        Entrega entrega = new Entrega(idFuncionario,codPedido);
        this.entregaMap.put(codPedido, entrega);
    }

    @Override
    public void notificaCliente(String IdCliente) {

    }

    @Override
    public void imprimirComprovativo(String cdPedido) {

    }

    @Override
    public void criarFichaCliente(String nome, String email, String nmr, String nmrUtente) {
        this.clientesMap.put(nome, new Cliente(nome, email ,nmr, nmrUtente));
    }
}
