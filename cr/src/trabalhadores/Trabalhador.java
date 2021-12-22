package trabalhadores;

public class Trabalhador {
    private String idTrabalhador;

    public Trabalhador(String idTrabalhador) {
        this.idTrabalhador = idTrabalhador;
    }

    public Trabalhador(Trabalhador trabalhador){
        this.idTrabalhador = trabalhador.getIdTrabalhador();
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
}
