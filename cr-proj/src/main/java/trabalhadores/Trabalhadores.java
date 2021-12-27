package trabalhadores;

import utils.SecurityUtil;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Trabalhadores implements ITrabalhadores {
    public static final Logger LOGGER = Logger.getLogger("CR");
    private Map<String,Trabalhador> trabalhadores;

    public Trabalhadores(){
        this.trabalhadores = new HashMap<>();
    }

    public Trabalhadores(Map<String, Trabalhador> trabalhadores){
        this.trabalhadores = trabalhadores
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry :: getKey, t -> t.getValue().clone()));
    }

    public Map<String,Trabalhador> getTrabalhadores() {
        return this.trabalhadores
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, t -> t.getValue().clone()));
    }

    @Override
    public Trabalhador doLogin(String id, String pass) {
        if (trabalhadores.containsKey(id)){
            Trabalhador t = this.trabalhadores.get(id);
            try {
                String passe = SecurityUtil.getStringSHA1(pass);
                if (t.getPasse().equals(passe)){
                    this.trabalhadores.get(id).setAutenticado(true);
                    return t;
                }
            } catch (NoSuchAlgorithmException e) {
                LOGGER.warning("Não foi encontrado o algoritmo SHA-1");
            }
        }
        return null;
    }

    @Override
    public boolean registarTrabalhador(String id, String passe, String confimaPasse) {
        if (trabalhadores.containsKey(id) || !passe.equals(confimaPasse)) return false;
        try {
            String pass = SecurityUtil.getStringSHA1(passe);
            Trabalhador t = new Trabalhador(id,pass);
            trabalhadores.put(id,t);
            return true;
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warning("Não foi encontrado o algoritmo SHA-1");
            return false;
        }
    }

    @Override
    public List<String> getListTecnicos() {
        return trabalhadores.values().stream().filter(t -> t instanceof Tecnico)
                .map(Trabalhador::getIdTrabalhador).collect(Collectors.toList());
    }

    public boolean verificarDisponibilidadeTecnicos(){
        return trabalhadores.values().stream()
                .anyMatch(trabalhador -> (
                        trabalhador instanceof Tecnico &&
                        trabalhador.isAutenticado() &&
                        ((Tecnico) trabalhador).isAvailable()
                ));
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
