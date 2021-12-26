package pedidos;

import java.time.LocalDateTime;
import java.util.Date;

public class Entrega {
    private LocalDateTime data;
    private String idPedido;
    private String idFuncionario;

    public Entrega (LocalDateTime data, String idPedido,String idFuncionario){
        this.data = data;
        this.idPedido= idPedido;
        this.idFuncionario = idFuncionario;
    }

    public Entrega (String idPedido,String idFuncionario){
        this.idPedido= idPedido;
        this.idFuncionario = idFuncionario;
        this.data = LocalDateTime.now();
    }
    
    public Entrega(Entrega entrega){
        this.data = entrega.getData();
        this.idPedido = entrega.getIdPedido();
        this.idFuncionario = entrega.getIdFuncionario();
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
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
