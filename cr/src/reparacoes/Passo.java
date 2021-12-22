package reparacoes;

import java.sql.Time;
import java.util.List;

public class Passo {
    private double custoPecas;
    private Time tempo;
    private List<Passo> subPassos;

    public Passo(double custoPecas, Time tempo, List<Passo> subPassos){
        this.custoPecas = custoPecas;
        this.tempo = tempo;
        this.subPassos = subPassos;
    }

    public double getCustoPecas() {
        return this.custoPecas;
    }

    public void setCustoPecas(double custoPecas) {
        this.custoPecas = custoPecas;
    }

    public Time getTempo() {
        return this.tempo;
    }

    public void setTempo(Time tempo) {
        this.tempo = tempo;
    }

    public List<Passo> getSubPassos() {
        return this.subPassos;
    }

    public void setSubPassos(List<Passo> subPassos) {
        this.subPassos = subPassos;
    }
}
