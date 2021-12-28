package trabalhadores;

import java.util.List;

public interface ITrabalhadores {
    Trabalhador doLogin(String id, String pass);

    boolean registarGestor(String id, String pass, String confirmaPass);

    boolean registarTecnico(String id, String pass, String confirmaPass);

    boolean registarFuncionario(String id, String pass, String confirmaPass);

    List<String> getListTecnicos();

    List<String> getListFuncionarios ();

    List<String> getListReparacoesByTecnico();

    List<String> getListIntervencoesByTecnico();

    int getNrRececaoEntregaByFuncionario();

    boolean verificarDisponibilidadeTecnicos();
}
