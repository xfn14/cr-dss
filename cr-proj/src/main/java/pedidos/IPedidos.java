package pedidos;

import exceptions.InvalidIdException;

import java.util.List;
import java.util.Map;

public interface IPedidos {
    List<String> getListPedidosOrcamento();

    List<String> getListEquipamentosLevantar();

    double getPrecoSE(String IdServicoExpresso) throws InvalidIdException;

    void cancelaPedido(String idPedido) throws InvalidIdException;

    void registaPedidoOrcamento(String codPedido) throws InvalidIdException;

    void registarContactoCliente(String idPedido, Contacto.Type tipo, String idFuncionario);

    void registaAceitacaoCliente(String idReparacao);

    boolean verificarDisponibilidadeSE(String idServicoExpresso) throws InvalidIdException;

    void registarSE(int idServicoExpresso, String idCliente);

    void atualizaDisponibilidadeSE(int idServicoExpresso);

    void adicionarParaLevantar(String idPedido);

    void entregaEquipamento(String codE, String idFuncionario);

    void notificaCliente(String IdCliente);

    void imprimirComprovativo(String cdPedido);

    void criarFichaCliente(String nome, String email, String nmr, String nmrUtente);

    Map.Entry<String, String> getNomeEmailCliente(String idPedido);
}
