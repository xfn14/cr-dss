package trabalhadores;

import java.util.List;
import java.util.Map;

public interface ITrabalhadores {
    Trabalhador doLogin(String id, String pass);

    Map<String, Trabalhador> getTrabalhadores();

    boolean registarGestor(String id, String pass, String confirmaPass);

    boolean registarTecnico(String id, String pass, String confirmaPass);

    boolean registarFuncionario(String id, String pass, String confirmaPass);

    List<String> getListTecnicos();

    List<String> getListFuncionarios();

    List<String> getListReparacoesByTecnico();

    List<String> getListIntervencoesByTecnico();

    int getNrRececaoEntregaByFuncionario();

    boolean verificarDisponibilidadeTecnicos();
}
