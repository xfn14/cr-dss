package pedidos;

import java.io.Serializable;

public class ServicoExpresso extends Pedido implements Serializable {
    private final String idTecnico;
    private Tipo tipo;
    private String descricao;

    public ServicoExpresso(String idPedido, String idCliente, String idEquipamento,
                           String idFuncionario, Tipo tipo, String idTecnico,
                           String descricao) {
        super(idPedido, idCliente, idEquipamento, idFuncionario);
        this.tipo = tipo;
        this.idTecnico = idTecnico;
        setEstado(Estado.DECORRER);
        this.descricao = descricao;
    }

    public Tipo getTipo() {
        return this.tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getIdTecnico() {
        return this.idTecnico;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public enum Tipo {
        FORMATAR_PC(10),
        INSTALAR_OS(10),
        SUBSTITUIR_ECRA(50),
        SUBSTITUIR_BATERIA(30),
        OUTRO(0);

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

        @Override
        public String toString(){
            if(this == FORMATAR_PC) return "Formatar PC";
            else if(this == INSTALAR_OS) return "Instalar OS";
            else if(this == SUBSTITUIR_ECRA) return "Substituir Ecra";
            else if(this == SUBSTITUIR_BATERIA) return "Substituir Bateria";
            else return "Outro";
        }
    }
}
