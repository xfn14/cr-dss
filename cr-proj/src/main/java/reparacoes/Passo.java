package reparacoes;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Passo implements Serializable {
    private double custoPecas;
    private Duration duration;
    private List<Passo> subPassos;


    public Passo(double custoPecas, Duration duration, List<Passo> subPassos) {
        this.custoPecas = custoPecas;
        this.duration = duration;
        this.subPassos = new ArrayList<>(subPassos);
    }

    public Passo(double horas, double custoPecas) {
        this.custoPecas = custoPecas;
        this.duration = Duration.ofHours((long) horas);
        this.subPassos = new ArrayList<>();
    }

    public void addSubPasso(double horas, double custoPecas) {
        this.custoPecas+= custoPecas;
        this.duration  = this.duration.plusHours((long) horas);
        Passo passo = new Passo(horas, custoPecas);
        subPassos.add(passo);
    }

    public double getCustoPecas() {
        return this.custoPecas;
    }

    public void setCustoPecas(double custoPecas) {
        this.custoPecas = custoPecas;
    }

    public List<Passo> getSubPassos() {
        return this.subPassos;
    }

    public void setSubPassos(List<Passo> subPassos) {
        this.subPassos = subPassos;
    }
}
