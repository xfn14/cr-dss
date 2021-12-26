package trabalhadores;

import java.util.List;

public interface ITrabalhadores {
    boolean doLogin(String id, String pass);
    boolean registarTrabalhador(String id, String passe, String confimaPasse);
    List<String> getListTecnicos();
    List<String> getListReparacoesByTecnico();
    List<String> getListIntervencoesByTecnico();
    int getNrRececaoEntregaByFuncionario();
}
