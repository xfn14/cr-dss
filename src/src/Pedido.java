import java.time.Instant;
import java.util.Date;
import java.util.List;

public class Pedido implements IPedidos{

    private Date data;
    private String idPedido;
    private String idServicoE;
    private String idCliente;
    private String idReparacao;
    private String idEquipamento;

    public Pedido(){
        this.data = Date.from(Instant.now());
        this.idPedido = "";
        this.idServicoE = "";
        this.idCliente = "";
        this.idReparacao = "";
        this.idEquipamento = "";
    }

    public Pedido(Date data, String idPedido, String idServicoE, String idCliente,
                  String idReparacao, String idEquipamento) {
        this.data = data;
        this.idPedido = idPedido;
        this.idServicoE = idServicoE;
        this.idCliente = idCliente;
        this.idReparacao = idReparacao;
        this.idEquipamento = idEquipamento;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getIdServicoE() {
        return idServicoE;
    }

    public void setIdServicoE(String idServicoE) {
        this.idServicoE = idServicoE;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdReparacao() {
        return idReparacao;
    }

    public void setIdReparacao(String idReparacao) {
        this.idReparacao = idReparacao;
    }

    public String getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(String idEquipamento) {
        this.idEquipamento = idEquipamento;
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
}
