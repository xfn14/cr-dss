package pedidos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Pedidos implements IPedidos {
    private List<Pedido> pedidos;

    public Pedidos(){
        this.pedidos = new ArrayList<>();
    }

    public Pedidos(Pedidos pedidos){
        this.pedidos = pedidos.getPedidos();
    }

    public void addPedido(Pedido pedido){
        this.pedidos.add(pedido);
    }

    public List<Pedido> getPedidos() {
        return this.pedidos;
    }

    @Override
    public List<String> getListPedidosOrcamento() {
        return null;
    }

    @Override
    public List<String> getListEquipamentosLevantar() {
        return null;
    }

    @Override
    public Float getPrecoSE(String IdServicoExpresso) {
        return null;
    }

    @Override
    public void cancelaPedido(String idPedido) {

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
    public boolean verificarDisponibilidadeSE(int idServicoExpresso) {
        return false;
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
    public void criarFichaCLiente(String nome, String email, String nmr) {

    }

    public Pedidos clone(){
        return new Pedidos(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedidos pedidos1 = (Pedidos) o;
        return Objects.equals(this.pedidos, pedidos1.getPedidos());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.pedidos);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pedidos{");
        sb.append("pedidos=").append(pedidos);
        sb.append('}');
        return sb.toString();
    }
}
