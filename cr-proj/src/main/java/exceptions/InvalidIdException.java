package exceptions;

public class InvalidIdException extends Exception {
    public InvalidIdException(String id, Type tipo) {
        super("Invalido id de " + tipo.getName() + " \"" + id + "\"");
    }

    public enum Type {
        PEDIDO("pedido"),
        SERVICO_EXPRESSO("serviço expresso"),
        PEDIDO_ORCAMENTO("pedido de orçamento"),
        REPARACAO("reparacao"),
        TRABALHADOR("trabalhador"),
        PLANO_TRABALHO("plano de trabalho");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
