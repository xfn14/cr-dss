package reparacoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Reparacoes implements IReparacoes {
    private List<Reparacao> reparacoes;

    public Reparacoes(){
        this.reparacoes = new ArrayList<>();
    }

    public Reparacoes(Reparacoes reparacoes){
        this.reparacoes = reparacoes.getReparacoes();
    }

    public List<Reparacao> getReparacoes() {
        return this.reparacoes.stream()
                .map(Reparacao::clone)
                .collect(Collectors.toList());
    }

    @Override
    public String createPLanosTrabalho() {
        return null;
    }

    @Override
    public String createPasso(float horas, float custoPecas) {
        return null;
    }

    @Override
    public void conclusaoPlanoDeTrabalho(String codPlanoDeTrabalho) {

    }

    @Override
    public String getPlanoDeTrabalho(String IdEquipamento) {
        return null;
    }

    @Override
    public void registaPasso(float horas, float custoPecas) {

    }

    @Override
    public void reparacaoParaEspera(String idReparacao) {

    }

    @Override
    public void registaConclusao(String idReparacao) {

    }

    public Reparacoes clone(){
        return new Reparacoes(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reparacoes that = (Reparacoes) o;
        return Objects.equals(this.reparacoes, that.getReparacoes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.reparacoes);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Reparacoes{");
        sb.append("reparacoes=").append(this.reparacoes);
        sb.append('}');
        return sb.toString();
    }
}
