import java.util.List;

public class Trabalhador implements ITrabalhadores{

    private String idTrabalhador;

    public Trabalhador(){
        this.idTrabalhador = "";
    }

    public Trabalhador(String idTrabalhador) {
        this.idTrabalhador = idTrabalhador;
    }

    public String getIdTrabalhador() {
        return idTrabalhador;
    }

    public void setIdTrabalhador(String idTrabalhador) {
        this.idTrabalhador = idTrabalhador;
    }

    @Override
    public boolean doLogin(String id, String pass) {
        return false;
    }

    @Override
    public void autenticarTrabalhador(String id) {

    }

    @Override
    public List<String> getListTecnicos() {
        return null;
    }

    @Override
    public List<String> getListReparacoesByTecnico() {
        return null;
    }

    @Override
    public List<String> getListIntervencoesByTecnico() {
        return null;
    }

    @Override
    public int getNrRececaoEntregaByFuncionario() {
        return 0;
    }
}
