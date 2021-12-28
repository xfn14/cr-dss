package pedidos;

import java.io.Serializable;
import java.util.List;

public class Cliente implements Serializable {
    private String nome;
    private String nmrUtente;
    private String nmr;
    private String email;
    private List<String> pedidos;

    public Cliente(String nome, String nmrUtente, String nmr, String email) {
        this.nome = nome;
        this.nmrUtente = nmrUtente;
        this.nmr = nmr;
        this.email = email;
    }

    public Cliente(Cliente cliente) {
        this.nome = cliente.getNome();
        this.nmrUtente = cliente.getNmrUtente();
        this.nmr = cliente.getNmr();
        this.email = cliente.getEmail();
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNmrUtente() {
        return this.nmrUtente;
    }

    public void setNmrUtente(String nmrUtente) {
        this.nmrUtente = nmrUtente;
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
