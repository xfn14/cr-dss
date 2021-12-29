package pedidos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cliente implements Serializable {
    private String nome;
    private String nif;
    private String nmr;
    private String email;
    private List<String> pedidos;

    public Cliente(String nome, String nmrUtente, String nmr, String email) {
        this.nome = nome;
        this.nif = nmrUtente;
        this.nmr = nmr;
        this.email = email;
        this.pedidos = new ArrayList<>();
    }

    public Cliente(Cliente cliente) {
        this.nome = cliente.getNome();
        this.nif = cliente.getNmrUtente();
        this.nmr = cliente.getNmr();
        this.email = cliente.getEmail();
        this.pedidos = cliente.getPedidos();
    }


    public void addPedido (String pedido){
        this.pedidos.add(pedido);
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNmrUtente() {
        return this.nif;
    }

    public void setNmrUtente(String nif) {
        this.nif = nif;
    }

    public String getNmr() {
        return this.nmr;
    }

    public void setNmr(String nmr) {
        this.nmr = nmr;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getPedidos() {
        return this.pedidos;
    }

    public void setPedidos(List<String> pedidos) {
        this.pedidos = pedidos;
    }

    public Cliente clone() {
        return new Cliente(this);
    }
}
