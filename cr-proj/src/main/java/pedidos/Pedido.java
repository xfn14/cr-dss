package pedidos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Pedido implements Serializable {
    private Date data;
    private String idPedido;
    private String idCliente;
    private String idEquipamento;
    private String idFuncionario;
    private Estado estado;
    private List<Contacto> contactos;

    public Pedido(Date data, String idPedido, String idCliente,
                  String idEquipamento, String idFuncionario, Estado estado) {
        this.data = data;
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.idEquipamento = idEquipamento;
        this.idFuncionario = idFuncionario;
        this.estado = estado;
        this.contactos = new ArrayList<>();
    }

    public Pedido(Pedido pedido) {
        this.data = pedido.getData() == null ? new Date() : new Date(pedido.getData().getTime());
        this.idPedido = pedido.getIdPedido();
        this.idCliente = pedido.getIdCliente();
        this.idEquipamento = pedido.getIdEquipamento();
        this.idFuncionario = pedido.getIdFuncionario();
        this.estado = pedido.getEstado();
        this.contactos = pedido.getContactos();
    }

    public enum Estado {
        DECORRER,
        FINALIZADO,
        CANCELADO
    }

    public void registaContacto(Contacto.Type tipo, String idFuncionario) {
        Contacto contacto = new Contacto(idFuncionario, tipo);
        contactos.add(contacto);
    }

    public String getIdFuncionario() {
        return this.idFuncionario;
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

    public List<Contacto> getContactos() {
        return this.contactos.stream().map(Contacto::clone).collect(Collectors.toList());
    }

    public Pedido clone() {
        return new Pedido(this);
    }
}
