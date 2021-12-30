package reparacoes;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Passo implements Serializable {
    private double custoPecas;
    private Duration duration;
    private List<Passo> subPassos;
    private String descricao;


    public Passo(double custoPecas, Duration duration, List<Passo> subPassos) {
        this.custoPecas = custoPecas;
        this.duration = duration;
        this.subPassos = new ArrayList<>(subPassos);
        this.descricao = "";
    }

    public Passo(double horas, double custoPecas) {
        this.custoPecas = custoPecas;
        this.duration = Duration.ofHours((long) horas);
        this.subPassos = new ArrayList<>();
        this.descricao="";
    }

    public Passo(double horas, double custoPecas, String descricao) {
        this.custoPecas = custoPecas;
        this.duration = Duration.ofHours((long) horas);
        this.subPassos = new ArrayList<>();
        this.descricao = descricao;
    }

    public void addSubPasso(double horas, double custoPecas,String descricao) {
        this.custoPecas += custoPecas;
        this.duration  = this.duration.plusHours((long) horas);
        Passo passo = new Passo(horas, custoPecas, descricao);
        this.subPassos.add(passo);
    }

    public Duration getDuration() {
        return this.duration;
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

    public String getDescricao() {
        return descricao;
    }


    @Override
    public String toString() {
        return "Passo{" +
                "custoPecas=" + custoPecas +
                ", duration=" + duration +
                ", subPassos=" + subPassos.stream().map(Passo::toString).collect(Collectors.toList()) +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
