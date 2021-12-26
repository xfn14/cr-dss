package pedidos;

import java.util.Date;

public class Pedido {
    private Date data;
    private String idPedido;
    private String idCliente;
    private String idEquipamento;
    private String idFuncionario;
    private Estado estado;

    public Pedido(Date data, String idPedido, String idCliente,
                  String idEquipamento, String idFuncionario, Estado estado) {
        this.data = data;
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.idEquipamento = idEquipamento;
        this.idFuncionario= idFuncionario;
        this.estado = estado;
    }

    public Pedido(Pedido pedido){
        this.data = pedido.getData() == null ? new Date() : new Date(pedido.getData().getTime());
        this.idPedido = pedido.getIdPedido();
        this.idCliente = pedido.getIdCliente();
        this.idEquipamento = pedido.getIdEquipamento();
        this.idFuncionario = pedido.getIdFuncionario();
        this.estado = pedido.getEstado();
    }

    public enum Estado {
        DECORRER,
        FINALIZADO,
        CANCELADO
    }

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
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


    public String getIdEquipamento() {
        return this.idEquipamento;
    }

    public void setIdEquipamento(String idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Pedido clone(){
        return new Pedido(this);
    }
}
