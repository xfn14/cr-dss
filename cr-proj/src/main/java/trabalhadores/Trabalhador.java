package trabalhadores;

import utils.SecurityUtil;

import java.security.NoSuchAlgorithmException;

public class Trabalhador {
    private String idTrabalhador;
    private String passe;
    private boolean autenticado;

    public Trabalhador(String idTrabalhador, String passe) {
        this.idTrabalhador = idTrabalhador;
        this.passe = passe;
        this.autenticado = false;
    }

    public Trabalhador(Trabalhador trabalhador){
        this.idTrabalhador = trabalhador.getIdTrabalhador();
        this.passe = trabalhador.getPasse();
        this.autenticado = trabalhador.isAutenticado();
    }

    public String getIdTrabalhador() {
        return this.idTrabalhador;
    }

    public void setIdTrabalhador(String idTrabalhador) {
        this.idTrabalhador = idTrabalhador;
    }

    public Trabalhador clone(){
        return new Trabalhador(this);
    }

    public String getPasse() { return this.passe; }

    public boolean isAutenticado() {
        return autenticado;
    }

    public void setAutenticado(boolean b){
        this.autenticado = b;
    }

    public void setPasse(String passe) throws NoSuchAlgorithmException {
        this.passe = SecurityUtil.getStringSHA1(passe);
    }
}
