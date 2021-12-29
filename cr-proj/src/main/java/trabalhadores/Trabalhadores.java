package trabalhadores;

import exceptions.SemTecnicosDisponiveis;
import utils.SecurityUtils;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Trabalhadores implements ITrabalhadores, Serializable {
    public static final Logger LOGGER = Logger.getLogger("CR");
    private final Map<String, Trabalhador> trabalhadores;

    public Trabalhadores() {
        this.trabalhadores = new HashMap<>();
    }

    public Trabalhadores(Map<String, Trabalhador> trabalhadores) {
        this.trabalhadores = trabalhadores
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, t -> t.getValue().clone()));
    }

    @Override
    public Map<String, Trabalhador> getTrabalhadores() {
        return this.trabalhadores
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, t -> t.getValue().clone()));
    }

    @Override
    public Trabalhador doLogin(String id, String pass) {
        if (trabalhadores.containsKey(id)) {
            Trabalhador t = this.trabalhadores.get(id);
            try {
                String passe = SecurityUtils.getStringSHA1(pass);
                if (t.getPasse().equals(passe)) {
                    this.trabalhadores.get(id).setAutenticado(true);
                    return t;
                }
            } catch (NoSuchAlgorithmException e) {
                LOGGER.warning("Não foi encontrado o algoritmo SHA-1");
            }
        }
        return null;
    }

    public Trabalhador criaTrabalhador(String id, String pass, String confirmaPass) {
        if (this.trabalhadores.containsKey(id) || !pass.equals(confirmaPass)) return null;
        try {
            String passe = SecurityUtils.getStringSHA1(pass);
            return new Trabalhador(id, passe);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warning("Não foi encontrado o algoritmo SHA-1");
        }
        return null;
    }

    @Override
    public boolean registarGestor(String id, String pass, String confirmaPass) {
        Trabalhador t = criaTrabalhador(id, pass, confirmaPass);
        if (t == null) return false;
        Gestor g = new Gestor(t);
        this.trabalhadores.put(id, g);
        return true;
    }

    @Override
    public boolean registarTecnico(String id, String pass, String confirmaPass) {
        Trabalhador t = criaTrabalhador(id, pass, confirmaPass);
        if (t == null) return false;
        Tecnico tec = new Tecnico(t);
        this.trabalhadores.put(id, tec);
        return true;
    }

    @Override
    public boolean registarFuncionario(String id, String pass, String confirmaPass) {
        Trabalhador t = criaTrabalhador(id, pass, confirmaPass);
        if (t == null) return false;
        Funcionario g = new Funcionario(t);
        this.trabalhadores.put(id, g);
        return true;
    }

    @Override
    public List<String> getListTecnicos() {
        return trabalhadores
                .values()
                .stream()
                .filter(t -> t instanceof Tecnico)
                .map(Trabalhador::getIdTrabalhador)
                .collect(Collectors.toList());
    }

    public Trabalhador verificarDisponibilidadeTecnicos() throws SemTecnicosDisponiveis {
        Trabalhador t = trabalhadores.values().stream()
                .filter(trabalhador -> (
                        trabalhador instanceof Tecnico &&
                        trabalhador.isAutenticado() &&
                        ((Tecnico) trabalhador).isAvailable()
                )).findAny().orElse(null);
        if (t == null) throw new SemTecnicosDisponiveis();
        return t;
    }

    @Override
    public List<String> getListReparacoesByTecnico() {
        return null;
    }

    public List<String> getListFuncionarios() {
        return trabalhadores.values().stream().
                filter(trabalhador -> trabalhador instanceof Funcionario).
                map(Trabalhador::getIdTrabalhador).
                collect(Collectors.toList());
    }

    @Override
    public List<String> getListIntervencoesByTecnico() {
        return null;
    }

    @Override
    public int getNrRececaoEntregaByFuncionario() {
        return 0;
    }

    public void addHorasTecnico(String idTecnico, long horas) {
        Tecnico tecnico = (Tecnico) this.trabalhadores.get(idTecnico);
        tecnico.addHoras(horas);
    }

    public void minusHorasTecnico(String idTecnico, long horas) {
        Tecnico tecnico = (Tecnico) this.trabalhadores.get(idTecnico);
        tecnico.minusHoras(horas);
    }

    public Duration getTrabalhoPorRealizarTecnico(String idTecnico) {
        Tecnico tecnico = (Tecnico) this.trabalhadores.get(idTecnico);
        return tecnico.getTrabalhoPorRealizar();
    }

    public void setNotAvailable(String idTecnico) {
        Tecnico tecnico = (Tecnico) this.trabalhadores.get(idTecnico);
        tecnico.setAvailable(false);
    }

    public void setAvailable(String idTecnico) {
        Tecnico tecnico = (Tecnico) this.trabalhadores.get(idTecnico);
        tecnico.setAvailable(true);
    }

}
