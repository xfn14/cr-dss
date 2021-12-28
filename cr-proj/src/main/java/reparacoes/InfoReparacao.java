package reparacoes;

import java.time.Duration;

public class InfoReparacao {
    private int numeroTotal;
    private Duration duracaoTotal;
    private long desvioDuracaoHoras;

    public InfoReparacao(){
        this.numeroTotal = 0;
        this.duracaoTotal = Duration.ZERO;
        this.desvioDuracaoHoras = 0;
    }

    public void addInfo(Duration duration, long desvio){
        this.numeroTotal++;
        this.duracaoTotal= this.duracaoTotal.plus(duration);
        this.desvioDuracaoHoras += desvio;
    }


}
