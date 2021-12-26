package reparacoes;

import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Passo {
    private double custoPecas;
    private Duration duration;
    private List<Passo> subPassos;

    public Passo( double custoPecas, Duration duration, List<Passo> subPassos){
        this.custoPecas = custoPecas;
        this.duration = duration;
        this.subPassos = subPassos.stream().collect(Collectors.toList());
    }

     public Passo(double horas,double custoPecas){
        this.custoPecas = custoPecas;
        this.duration = Duration.ofHours((long) horas);
        this.subPassos = new ArrayList<>();
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
