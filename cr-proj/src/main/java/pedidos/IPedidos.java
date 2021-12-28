package pedidos;

import exceptions.InvalidIdException;

import java.util.List;
import java.util.Map;

public interface IPedidos {
    List<String> getListPedidosOrcamento();

    List<String> getListEquipamentosLevantar();

    double getPrecoSE(String IdServicoExpresso) throws InvalidIdException;

    void cancelaPedido(String idPedido) throws InvalidIdException;

    void registaPedidoOrcamento(String idCliente,String idFuncionario,String descricao);

    void registaAceitacaoCliente(String idReparacao);

    boolean verificarDisponibilidadeSE(String idServicoExpresso) throws InvalidIdException;

    //void atualizaDisponibilidadeSE(int idServicoExpresso);

    void adicionarParaLevantar(String idPedido);

    void entregaEquipamento(String codE, String idFuncionario);

    void imprimirComprovativo(String cdPedido);

    void criarFichaCliente(String nome, String email, String nmr, String nif);

    Map.Entry<String, String> getNomeEmailCliente(String idPedido);

    void registarContactoParaLevantar(String idPedido,String idFuncionario);

    void registarContactoSemReparacao(String idPedido,String idFuncionario);

    void registarContactoValorSuperior(String idPedido,String idFuncionario);

    void registarContactoPedidoOrcamento(String idPedido,String idFuncionario);

    void registarFormatarPC(ServicoExpresso.Tipo tipo, String idCliente, String idFuncionario);

    void registarInstalarOS(ServicoExpresso.Tipo tipo, String idCliente, String idFuncionario);

    void registarSubstituirEcra(ServicoExpresso.Tipo tipo, String idCliente, String idFuncionario);

    void registarSubstituirBateria(ServicoExpresso.Tipo tipo, String idCliente, String idFuncionario);

    void conclusaoPlanoTrabalho(String idPedido);

    List<Map.Entry<String,String>> aguardaResposta ();
}
