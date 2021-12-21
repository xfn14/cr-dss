package pedidos;

public class PedidoOrcamento extends Pedido {
    public PedidoOrcamento(){
        super();
    }

    public PedidoOrcamento(PedidoOrcamento pedO){
        super(pedO.getData(), pedO.getIdPedido(), pedO.getIdCliente(), pedO.getIdReparacao(), pedO.getIdEquipamento());
    }

    public PedidoOrcamento clone(){
        return new PedidoOrcamento(this);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
