package exceptions;

public class InvalidIdException extends Exception {
    public InvalidIdException(String id, Type tipo){
        super("Invalido id de " + tipo.getName() + " \"" + tipo + "\"");
    }

    public enum Type {
        PEDIDO("pedido"),
        REPARACAO("reparacao"),
        TRABALHADOR("trabalhador");

        private final String name;

        Type(String name){
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
