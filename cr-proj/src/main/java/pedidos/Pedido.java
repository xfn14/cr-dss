package pedidos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Pedido implements Serializable {
    private LocalDateTime data;
    private String idPedido;
    private String idCliente;
    private String idEquipamento;
    private String idFuncionario;
    private Estado estado;
    private List<Contacto> contactos;

    public Pedido(String idPedido, String idCliente, String idEquipamento, String idFuncionario) {
        this.data = LocalDateTime.now();
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.idEquipamento = idEquipamento;
        this.idFuncionario = idFuncionario;
        this.estado = Estado.AGUARDA_PLANO;
        this.contactos = new ArrayList<>();
    }

    public Pedido(Pedido pedido) {
        this.idPedido = pedido.getIdPedido();
        this.idCliente = pedido.getIdCliente();
        this.idEquipamento = pedido.getIdEquipamento();
        this.idFuncionario = pedido.getIdFuncionario();
        this.estado = pedido.getEstado();
        this.contactos = pedido.getContactos();
    }

    public void registaContacto(Contacto.Type tipo, String idFuncionario) {
        Contacto contacto = new Contacto(idFuncionario, tipo);
        contactos.add(contacto);
    }

    public boolean aguardaReparacao() {
        return estado.equals(Estado.AGUARDA_ACEITACAO);
    }

    public boolean isFinalizado(){
        return this.estado.equals(Estado.FINALIZADO);
    }

    public boolean aguardaPlano(){
        return this.estado.equals(Estado.AGUARDA_PLANO);
    }

    public LocalDateTime getDataContactoPedidoOrcamento(){
        for (Contacto contacto : contactos){
            if (contacto.getType().equals(Contacto.Type.PEDIDO_ORCAMENTO)){
                return contacto.getDateTime();
            }
        }
        return LocalDateTime.now();
    }
    public LocalDateTime getDataContactoValorSuperior(){
        for (Contacto contacto : contactos){
            if (contacto.getType().equals(Contacto.Type.VALOR_SUPERIOR)){
                return contacto.getDateTime();
            }
        }
        return LocalDateTime.now();
    }



    public String getIdFuncionario() {
        return this.idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
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

    public LocalDateTime getData() {
        return data;
    }

    public Pedido clone() {
        return new Pedido(this);
    }

    public enum Estado {
        DECORRER,
        PAUSA,
        AGUARDA_PLANO,
        AGUARDA_ACEITACAO,
        AGUARDA_REPARACAO,
        AGUARDA_ENTREGA,
        FINALIZADO,
        ARQUIVADO,
        CANCELADO
    }
}
