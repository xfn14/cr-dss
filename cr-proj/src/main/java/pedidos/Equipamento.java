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
}
