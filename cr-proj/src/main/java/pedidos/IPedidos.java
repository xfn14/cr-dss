package pedidos;

import exceptions.InvalidIdException;
import exceptions.JaExisteException;
import exceptions.SemPedidosOrcamento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IPedidos {
    List<String> getListPedidosOrcamento();

    List<String> getListEquipamentosLevantar();

    double getPrecoSE(String IdServicoExpresso) throws InvalidIdException;

    void cancelaPedido(String idPedido) throws InvalidIdException;

    void registaPedidoOrcamento(String idCliente, String idFuncionario, String descricao);

    void registaAceitacaoCliente(String idReparacao);

    void finalizaPedido(String idPedido);

    void entregaEquipamento(String codE, String idFuncionario);

    void criarFichaCliente(String nome, String email, String nmr, String nif) throws JaExisteException;

    Map.Entry<String, String> getNomeEmailCliente(String idPedido);

    void registarContactoParaLevantar(String idPedido, String idFuncionario);

    void registarContactoSemReparacao(String idPedido, String idFuncionario);

    void registarContactoValorSuperior(String idPedido, String idFuncionario);

    void registarContactoPedidoOrcamento(String idPedido, String idFuncionario);

    void registarFormatarPC(String idCliente, String idFuncionario, String idTecnico, String descricao);

    void registarInstalarOS(String idCliente, String idFuncionario, String idTecnico, String descricao);

    void registarSubstituirEcra(String idCliente, String idFuncionario, String idTecnico, String descricao);

    void registarSubstituirBateria(String idCliente, String idFuncionario, String idTecnico, String descricao);

    void registarOutro(String idCliente, String idFuncionario, String idTecnico, String descricao);

    void pedidoAguardaAceitacao(String idPedido);

    Map<String, Integer> getNrPedidosByFuncionario(LocalDateTime month);

    Map<String, Integer> getNrEntregasByFuncionario(LocalDateTime month);

    List<String> getPedidosConcluidosMonth(LocalDateTime month);

    Map<String, Integer> getNrServicosExpressoMonth(LocalDateTime month, List<String> pedidos);

    Map<String, Map.Entry<String, LocalDateTime>> aguardaResposta();

    Map<String, List<String>> getServicosExpressoByTecnico(List<String> pedidos);

    List<String> getClientesId();

    List<Pedido> getPedidos();

    List<Cliente> getClientes();

    void pedidoADecorrer(String idPedido);

    String getPedidoOrcamentoMaisAntigo() throws SemPedidosOrcamento;

    boolean isClienteAutenticado(String idCliente);

    String getDescricaoPedido(String idPedido);

    boolean isValidPedidoID(String idPedido);

    boolean hasEquipamenteEntregue(String idPedido);

    LocalDateTime getDataContactoValorSuperior(String id);

    void arquivarPedido(String idPedido);

    void pedidoAtualizaIdPlano(String idPedido);

    String getIdTecnicoSE(String idPedido) throws InvalidIdException;

    void pedidoParaPausa(String idPedido);

    void changeOrcamento(String idPlano,double orcamento);

    void conclusaoReparacao (String idPlano);

    }

