package pedidos;

import java.io.Serializable;

public class ServicoExpresso extends Pedido implements Serializable {
    private String idServicoE;
    private Tipo tipo;

    public ServicoExpresso(Pedido pedido, String idServicoE) {
        super(pedido);
        this.idServicoE = idServicoE;
    }


    public ServicoExpresso(String idPedido,String idCliente,String idEquipamento,String idFuncionario,Tipo tipo){
        super(idPedido,idCliente,idEquipamento,idFuncionario);
        this.tipo = tipo;
        setEstado(Estado.DECORRER);
    }

    public enum Tipo {
        FORMATAR_PC(10),
        INSTALAR_OS(10),
        SUBSTITUIR_ECRA(50),
        SUBSTITUIR_BATERIA(30);

        private double precoBase;

        Tipo(double precoBase) {
            this.precoBase = precoBase;
        }

        public void addPreco(double preco) {
            this.precoBase += preco;
        }

        public double getPreco() {
            return this.precoBase;
        }
    }

    public String getIdServicoE() {
        return this.idServicoE;
    }

    public void setIdServicoE(String idServicoE) {
        this.idServicoE = idServicoE;
    }

    public Tipo getTipo() {
        return this.tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

}
