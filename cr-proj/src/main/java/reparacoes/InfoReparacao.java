package reparacoes;

import java.time.Duration;

public class InfoReparacao {
    private int numeroTotalReparacoes;
    private Duration duracaoTotal;
    private long desvioDuracaoHoras;

    public InfoReparacao() {
        this.numeroTotalReparacoes = 0;
        this.duracaoTotal = Duration.ZERO;
        this.desvioDuracaoHoras = 0;
    }

    public void addInfo(Duration duration, long desvio) {
        this.numeroTotalReparacoes++;
        this.duracaoTotal = this.duracaoTotal.plus(duration);
        this.desvioDuracaoHoras += desvio;
    }

    public int getNumeroTotalReparacoes() {
        return numeroTotalReparacoes;
    }

    public long duracaoMedia(){
        return duracaoTotal.dividedBy(numeroTotalReparacoes).toHours();
    }

    public long mediaDesvio(){
        return desvioDuracaoHoras/ numeroTotalReparacoes;
    }
}
