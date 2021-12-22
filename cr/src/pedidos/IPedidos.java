package pedidos;

import exceptions.InvalidIdException;

import java.util.Date;
import java.util.List;

public interface IPedidos {
    List<String> getListPedidosOrcamento();
    List<String> getListEquipamentosLevantar();
    double getPrecoSE(String IdServicoExpresso) throws InvalidIdException;
    void cancelaPedido(String idPedido) throws InvalidIdException;
    void registaPedidoOrcamento(String codPedido);
    void registarContactoCliente(String idCliente, Date data);
    void registaAceitacaoCliente(String idReparacao);
    boolean verificarDisponibilidadeSE(int idServicoExpresso);
    void registarSE(int idServicoExpresso, String idCliente);
    void atualizaDisponibilidadeSE(int idServicoExpresso);
    void adicionarParaLevantar(String idEquipamento);
    void entregaEquipamento(String codE);
    void notificaCliente(String IdCliente );
    void imprimirComprovativo(String cdPedido);
    void criarFichaCliente(String nome, String email, String nmr);
}
