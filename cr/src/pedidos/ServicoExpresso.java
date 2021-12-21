package pedidos;

import java.util.Objects;

public class ServicoExpresso extends Pedido {
    private String idServicoE;

    public ServicoExpresso(Pedido pedido, String idServicoE){
        super(pedido);
        this.idServicoE = idServicoE;
    }

    public ServicoExpresso(ServicoExpresso serE){
        super(serE.getData(), serE.getIdPedido(), serE.getIdCliente(), serE.getIdReparacao(), serE.getIdEquipamento());
        this.idServicoE = serE.getIdServicoE();
    }

    public String getIdServicoE() {
        return this.idServicoE;
    }

    public void setIdServicoE(String idServicoE) {
        this.idServicoE = idServicoE;
    }

    public ServicoExpresso clone(){
        return new ServicoExpresso(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ServicoExpresso that = (ServicoExpresso) o;
        return Objects.equals(this.idServicoE, that.getIdServicoE());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.idServicoE);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ServicoExpresso{");
        sb.append("idServicoE='").append(this.idServicoE).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
