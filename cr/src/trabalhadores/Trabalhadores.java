package trabalhadores;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Trabalhadores implements ITrabalhadores{
    private List<Trabalhador> trabalhadores;

    public Trabalhadores(){
        this.trabalhadores = new ArrayList<>();
    }

    public Trabalhadores(Trabalhadores trabalhadores){
        this.trabalhadores = trabalhadores.getTrabalhadores();
    }

    public List<Trabalhador> getTrabalhadores() {
        return this.trabalhadores.stream()
                .map(Trabalhador::clone)
                .collect(Collectors.toList());
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
