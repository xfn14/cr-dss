package pedidos;

import java.util.Objects;

public class Equipamento {
    private String idEquipamento;

    public Equipamento(){
        this.idEquipamento = "";
    }

    public Equipamento(String idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public Equipamento(Equipamento equipamento){
        this.idEquipamento = equipamento.getIdEquipamento();
    }

    public String getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(String idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public Equipamento clone(){
        return new Equipamento(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipamento that = (Equipamento) o;
        return Objects.equals(this.idEquipamento, that.getIdEquipamento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idEquipamento);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Equipamento{");
        sb.append("idEquipamento='").append(this.idEquipamento).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
