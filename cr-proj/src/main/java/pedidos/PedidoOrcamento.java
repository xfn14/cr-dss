package pedidos;

import java.io.Serializable;

public class PedidoOrcamento extends Pedido implements Serializable {
    private double orcamento;
    private String idPlanoTrabalho;
    private String descricaoProblema;


    public PedidoOrcamento(String idPedido, String idCliente, String idFuncionario, String idEquipamento, String descricao) {
        super(idPedido, idCliente, idEquipamento, idFuncionario);
        this.descricaoProblema = descricao;
        setEstado(Estado.AGUARDA_PLANO);
    }

    public double getOrcamento() {
        return this.orcamento;
    }

    public void setOrcamento(double orcamento) {
        this.orcamento = orcamento;
    }

    public String getIdPlanoTrabalho() {
        return this.idPlanoTrabalho;
    }

    public void setIdPlanoTrabalho(String idPlanoTrabalho) {
        this.idPlanoTrabalho = idPlanoTrabalho;
    }

}
