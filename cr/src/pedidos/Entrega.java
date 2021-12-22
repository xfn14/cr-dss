package pedidos;

import java.util.Date;

public class Entrega {
    private Date data;
    private String idPedido;
    private String idFuncionario;

    public Entrega (Date data, String idPedido,String idFuncionario){
        this.data = data;
        this.idPedido= idPedido;
        this.idFuncionario = idFuncionario;
    }

    public Entrega(Entrega entrega){
        this.data = entrega.getData();
        this.idPedido = entrega.getIdPedido();
        this.idFuncionario = entrega.getIdFuncionario();
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

    public String getIdFuncionario() {
        return this.idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Entrega clone(){
        return new Entrega(this);
    }
}
