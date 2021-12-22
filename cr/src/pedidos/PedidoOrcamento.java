package pedidos;

public class PedidoOrcamento extends Pedido {
    private double orcamento;
    private String idPlanoTrabalho;

    public PedidoOrcamento(Pedido pedido, double orcamento, String idPlanoTrabalho){
        super(pedido.getData(), pedido.getIdPedido(), pedido.getIdCliente(), pedido.getIdReparacao(),
              pedido.getIdEquipamento(), pedido.getIdFuncionario(), pedido.getEstado());
        this.orcamento = orcamento;
        this.idPlanoTrabalho = idPlanoTrabalho;
    }

    public PedidoOrcamento(PedidoOrcamento pedO){
        super(pedO.getData(), pedO.getIdPedido(), pedO.getIdCliente(), pedO.getIdReparacao(),
              pedO.getIdEquipamento(), pedO.getIdFuncionario(), pedO.getEstado());
        this.orcamento = pedO.getOrcamento();
        this.idPlanoTrabalho = pedO.getIdPlanoTrabalho();
    }

    public double getOrcamento() {
        return this.orcamento;
    }

    public void setOrcamento(double orcamento) {
        this.orcamento = orcamento;
    }

    public String getIdPlanoTrabalho() {
        return this.idPlanoTrabalho;
    }

    public void setIdPlanoTrabalho(String idPlanoTrabalho) {
        this.idPlanoTrabalho = idPlanoTrabalho;
    }

    public PedidoOrcamento clone(){
        return new PedidoOrcamento(this);
    }
}
