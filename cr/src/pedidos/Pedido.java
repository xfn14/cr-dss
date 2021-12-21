package pedidos;

import java.util.Date;
import java.util.Objects;

public class Pedido {
    private Date data;
    private String idPedido;
    private String idCliente;
    private String idReparacao;
    private String idEquipamento;

    public Pedido(){
        this.data = new Date();
        this.idPedido = "";
        this.idCliente = "";
        this.idReparacao = "";
        this.idEquipamento = "";
    }

    public Pedido(Date data, String idPedido, String idCliente,
                  String idReparacao, String idEquipamento) {
        this.data = data;
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.idReparacao = idReparacao;
        this.idEquipamento = idEquipamento;
    }

    public Pedido(Pedido pedido){
        this.data = pedido.getData() == null ? new Date() : new Date(pedido.getData().getTime());
        this.idPedido = pedido.getIdPedido();
        this.idCliente = pedido.getIdCliente();
        this.idReparacao = pedido.getIdReparacao();
        this.idEquipamento = pedido.getIdEquipamento();
    }

    public Date getData() {
        return new Date(this.data.getTime());
    }

    public void setData(Date data) {
        this.data = new Date(data.getTime());
    }

    public String getIdPedido() {
        return this.idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdReparacao() {
        return this.idReparacao;
    }

    public void setIdReparacao(String idReparacao) {
        this.idReparacao = idReparacao;
    }

    public String getIdEquipamento() {
        return this.idEquipamento;
    }

    public void setIdEquipamento(String idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public Pedido clone(){
        return new Pedido(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return this.data.getTime() == pedido.getData().getTime() &&
               this.idPedido.equals(pedido.getIdPedido()) &&
               this.idCliente.equals(pedido.getIdCliente()) &&
               this.idReparacao.equals(pedido.getIdReparacao()) &&
               this.idEquipamento.equals(pedido.getIdEquipamento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.data, this.idPedido, this.idCliente, this.idReparacao, this.idEquipamento);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pedido{");
        sb.append("data=").append(this.data);
        sb.append(", idPedido='").append(this.idPedido).append('\'');
        sb.append(", idCliente='").append(this.idCliente).append('\'');
        sb.append(", idReparacao='").append(this.idReparacao).append('\'');
        sb.append(", idEquipamento='").append(this.idEquipamento).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
