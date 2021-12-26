package pedidos;

public class ServicoExpresso extends Pedido {
    private String idServicoE;
    private Tipo tipo;

    public ServicoExpresso(Pedido pedido, String idServicoE){
        super(pedido);
        this.idServicoE = idServicoE;
    }

    public ServicoExpresso(ServicoExpresso serE){
        super(serE.getData(), serE.getIdPedido(), serE.getIdCliente(),
              serE.getIdEquipamento(), serE.getIdFuncionario(), serE.getEstado());
        this.idServicoE = serE.getIdServicoE();
    }

    public enum Tipo {
        FORMATAR_PC(10),
        INSTALAR_OS(10),
        SUBSTITUIR_ECRA(50),
        SUBSTITUIR_BATERIA(30);

        private double precoBase;

        Tipo(double precoBase){
            this.precoBase = precoBase;
        }

        public void addPreco(double preco){
            this.precoBase += preco;
        }

        public double getPreco() {
            return this.precoBase;
        }
    }

    public String getIdServicoE() {
        return this.idServicoE;
    }

    public void setIdServicoE(String idServicoE) {
        this.idServicoE = idServicoE;
    }

    public Tipo getTipo() {
        return this.tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public ServicoExpresso clone(){
        return new ServicoExpresso(this);
    }
}
