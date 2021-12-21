package trabalhadores;

import java.util.List;

public interface ITrabalhadores {
    boolean doLogin(String id, String pass);
    void autenticarTrabalhador(String id);
    List<String> getListTecnicos();
    List<String> getListReparacoesByTecnico();
    List<String> getListIntervencoesByTecnico();
    int getNrRececaoEntregaByFuncionario();
}
