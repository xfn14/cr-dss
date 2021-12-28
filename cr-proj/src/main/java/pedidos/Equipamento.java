package pedidos;

import java.io.Serializable;

public class Equipamento implements Serializable {
    private String idEquipamento;

    public Equipamento(String idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public Equipamento(Equipamento equipamento) {
        this.idEquipamento = equipamento.getIdEquipamento();
    }

    public String getIdEquipamento() {
        return this.idEquipamento;
    }

    public void setIdEquipamento(String idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public Equipamento clone() {
        return new Equipamento(this);
    }
}
