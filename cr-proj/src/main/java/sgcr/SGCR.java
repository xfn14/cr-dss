package sgcr;

import emailHandler.Email;
import exceptions.InvalidIdException;
import exceptions.ValorSuperior;
import pedidos.IPedidos;
import pedidos.Pedidos;
import reparacoes.IReparacoes;
import reparacoes.PlanoTrabalho;
import reparacoes.Reparacoes;
import trabalhadores.ITrabalhadores;
import trabalhadores.Trabalhador;
import trabalhadores.Trabalhadores;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SGCR implements Serializable {
    private ITrabalhadores trabalhadores;
    private IPedidos pedidos;
    private IReparacoes reparacoes;

    public SGCR() {
        this.trabalhadores = new Trabalhadores();
        this.pedidos = new Pedidos();
        this.reparacoes = new Reparacoes();
    }

    public ITrabalhadores getTrabalhadores() {
        return this.trabalhadores;
    }

    public void setTrabalhadores(ITrabalhadores trabalhadores) {
        this.trabalhadores = trabalhadores;
    }

    public IPedidos getPedidos() {
        return this.pedidos;
    }

    public void setPedidos(IPedidos pedidos) {
        this.pedidos = pedidos;
    }

    public IReparacoes getReparacoes() {
        return this.reparacoes;
    }

    public void setReparacoes(IReparacoes reparacoes) {
        this.reparacoes = reparacoes;
    }


    public void registaConclusaoReparacao(String idReparacao) throws InvalidIdException {
        reparacoes.registaConclusao(idReparacao);
        Map.Entry<String, String> entry = pedidos.getNomeEmailCliente(idReparacao);
        String email = entry.getValue();
        String nome = entry.getKey();
        //Informar o cliente que o equipamento está pronto a levantar
        Email.prontoALevantar(email, nome);
    }

    public void registaEquipamentoSemReparacao(String idPedido) throws InvalidIdException {
        pedidos.cancelaPedido(idPedido);
        Map.Entry<String, String> entry = pedidos.getNomeEmailCliente(idPedido);
        String email = entry.getValue();
        String nome = entry.getKey();
        //Informar o cliente que o equipamento não tem reparação
        Email.naoPodeSerReparado(email, nome);
    }

    public void registaConclusaoPlanoTrabalho() {
        //this.pedidos.registaConclusaoPlanoTrabalho();
    }

    public Trabalhador doLogin(String username, String passe) {
        return trabalhadores.doLogin(username, passe);
    }

    public boolean registarGestor(String id, String pass, String confirmaPass){
        return this.trabalhadores.registarGestor(id, pass, confirmaPass);
    }

    public boolean registarTecnico(String id, String pass, String confirmaPass){
        return this.trabalhadores.registarTecnico(id, pass, confirmaPass);
    }

    public boolean registarFuncionario(String id, String pass, String confirmaPass){
        return this.trabalhadores.registarFuncionario(id, pass, confirmaPass);
    }

    public List<String> getListTecnicos() {
        return trabalhadores.getListTecnicos();
    }

    public boolean verificarDisponibilidadeTecnicos() {
        return trabalhadores.verificarDisponibilidadeTecnicos();
    }

    public List<String> getListPedidosOrcamento() {
        return pedidos.getListPedidosOrcamento();
    }

    public List<String> getListEquipamentosLevantar() {
        return pedidos.getListEquipamentosLevantar();
    }

    public double getPrecoSE(String idServicoExpresso) throws InvalidIdException {
        return pedidos.getPrecoSE(idServicoExpresso);
    }

    public boolean verificarDisponibilidadeSE(String idServicoExpresso) throws InvalidIdException {
        return pedidos.verificarDisponibilidadeSE(idServicoExpresso);
    }

    public void cancelaPedido(String idPedido) throws InvalidIdException {
        this.pedidos.cancelaPedido(idPedido);
    }

    public void registaPedidoOrcamento(String codPedido) throws InvalidIdException {
        this.pedidos.registaPedidoOrcamento(codPedido);
    }

    public void criarFichaCliente(String nome, String email, String nmr, String nmrUtente) {
        this.pedidos.criarFichaCliente(nome, email, nmr, nmrUtente);
    }

    public Map.Entry<String, String> getNomeEmailCliente(String idPedido) {
        return this.pedidos.getNomeEmailCliente(idPedido);
    }

    public void entregaEquipamento(String codPedido, String idFuncionario) {
        this.pedidos.entregaEquipamento(codPedido, idFuncionario);
    }

    public void adicionarParaLevantar(String idPedido) {
        this.pedidos.adicionarParaLevantar(idPedido);
    }


    /*
    public void registarContactoCliente  (String idPedido, Contacto.Type tipo, String idFuncionario) {
        this.pedidos.registarContactoCliente(idPedido,tipo,idFuncionario);
    }


     */


    public void createPlanosTrabalho(String idPedido) {
        this.reparacoes.createPlanosTrabalho(idPedido);
    }

    public void registaPasso(double horas, double custoPecas, String idReparacao) throws InvalidIdException {
        try {
            this.reparacoes.registaPasso(horas, custoPecas, idReparacao);
        }
        catch (ValorSuperior e){
            Map.Entry<String, String> entry = pedidos.getNomeEmailCliente(idReparacao);
            String email = entry.getValue();
            String nome = entry.getKey();
            this.reparacoes.reparacaoParaEspera(idReparacao);

            Email.valorSuperiorOrcamento(email,nome);
        }
    }

    public void addPasso(String idPlano, double horas, double custoPecas) throws InvalidIdException {
        this.reparacoes.addPasso(idPlano, horas, custoPecas);
    }

    public PlanoTrabalho getPlanoDeTrabalho(String idPedido) {
        return this.reparacoes.getPlanoDeTrabalho(idPedido);
    }

    public void reparacaoParaEspera(String idReparacao) throws InvalidIdException {
        this.reparacoes.reparacaoParaEspera(idReparacao);
    }

    public void registaConclusao(String idReparacao) throws InvalidIdException {
        this.reparacoes.registaConclusao(idReparacao);
    }

    public void conclusaoPlanoDeTrabalho(String IdPedido) throws InvalidIdException {
        this.reparacoes.conclusaoPlanoDeTrabalho(IdPedido);
    }


    public void criaReparacao (String idReparacao){
        double orcamento = this.reparacoes.getOrcamento(idReparacao);
        //this.reparacoes.
    }





    public List<String> getListReparacoesByTecnico() {
        return null;
    }

    public List<String> getListIntervencoesByTecnico() {
        return null;
    }

    public int getNrRececaoEntregaByFuncionario() {
        return 0;
    }
}