package pedidos;

import java.util.List;

public class Cliente {
    private String nome;
    private String nmrUtente;
    private String nmr;
    private String email;
    private List<String> pedidos;

    public Cliente() {
        this.nome = "";
        this.nmrUtente = "";
        this.nmr = "";
        this.email = "";
    }

    public Cliente (String nome , String nmrUtente, String nmr, String email){
        this.nome = nome;
        this.nmrUtente = nmrUtente;
        this.nmr = nmr;
        this.email = email;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNmrUtente() {
        return nmrUtente;
    }

    public void setNmrUtente(String nmrUtente) {
        this.nmrUtente = nmrUtente;
    }

    public String getNmr() {
        return nmr;
    }

    public void setNmr(String nmr) {
        this.nmr = nmr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<String> pedidos) {
        this.pedidos = pedidos;
    }
}
