package trabalhadores;

import exceptions.JaExisteException;
import exceptions.PasswordErradaException;
import exceptions.SemTecnicosDisponiveis;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public interface ITrabalhadores {
    Trabalhador doLogin(String id, String pass);

    Map<String, Trabalhador> getTrabalhadores();

    boolean registarGestor(String id, String pass, String confirmaPass) throws JaExisteException, PasswordErradaException;

    boolean registarTecnico(String id, String pass, String confirmaPass) throws JaExisteException, PasswordErradaException;

    boolean registarFuncionario(String id, String pass, String confirmaPass) throws JaExisteException, PasswordErradaException;

    List<String> getListTecnicos();

    List<String> getListFuncionarios();



    Trabalhador verificarDisponibilidadeTecnicos() throws SemTecnicosDisponiveis;

    void addHorasTecnico(String idTecnico, long horas);

    void minusHorasTecnico(String idTecnico, long horas);

    Duration getTrabalhoPorRealizarTecnico(String idTecnico);

    void setNotAvailable(String idTecnico);

    void setAvailable(String idTecnico);

    }
