public class Reparacao implements IReparacoes{

    private String idReparacao;

    public Reparacao(){
        this.idReparacao = "";
    }

    public Reparacao(String idReparacao){
        this.idReparacao = idReparacao;
    }

    public String getIdReparacao() {
        return idReparacao;
    }

    public void setIdReparacao(String idReparacao) {
        this.idReparacao = idReparacao;
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
}
