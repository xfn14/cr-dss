package emailHandler;

import sgcr.SGCR;

import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Email implements Runnable {
    private boolean running = true;
    private SGCR sgcr;
    //private LocalDate date  TODO: Ver o dia e se ainda não tiver corrido neste dia, correr a verificação para ausência de resposta| Arquivar Pedido


    public Email(SGCR sgcr) {
        this.sgcr = sgcr;
    }


    private static String password = "grupoG35";
    private static String user = "sgcrgrupo35@gmail.com";
    /*
        Mensages:
        - Equipamento não pode ser reparado
        - Equipamento pronto a ser Levantado
        - Valor da reparação superior 120% do valor orçamentado
        - Resposta plano Trabalho
     */

    private static final int pedidoOrcamento = 0;
    private static final int naoPodeSerReparado = 1;
    private static final int prontoaLevantar = 2;
    private static final int valorSuperiorOrcamento = 3;


    private static String subject(int tipo) {
        String result = "";
        if (tipo == pedidoOrcamento) result = "Pedido de Orçamento";
        if (tipo == naoPodeSerReparado) result = "Equipamento Sem Reparação";
        if (tipo == prontoaLevantar) result = "Equipamento pronto a ser levantado";
        if (tipo == valorSuperiorOrcamento) result = "Valor Superior ao Orçamento";
        return "SGCR - " + result;
    }

    private static String mesageOrcamento(String nome, double orcamento, Duration duration) {
        String days = duration.toDays() == 0? "" : duration.toDays() + " dia(s) ";
        String hours = duration.toHours() == 0? "" : duration.toHoursPart() + " horas";
        return "Caro " + nome + ",\n\n" +
                "Informamos que o orçamento relativo ao seu pedido será " + orcamento + " euros.\n" +
                "E que serão necessárias aproximadamente " + days + hours + " de trabalho.\n" +
                "Responda a este email caso aceite.\n" +
                "Ignore caso contrário. E faça o levantamento do equipamento na loja\n" +
                "\nCumprimentos, SGCR.";
    }


    private static String message(int tipo, String nome) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Caro ").append(nome).append(",\n\n");
        switch (tipo) {
            case naoPodeSerReparado:
                stringBuilder.append("Lamentamos informar que o equipamento relativo ao seu pedido não tem reparação\n\n");
                stringBuilder.append("Fica disponível para ser levantado na loja.\n");
                break;
            case prontoaLevantar:
                stringBuilder.append("Informamos que o seu equipamento está pronto a ser levantado na loja.\n");
                break;
            case valorSuperiorOrcamento:
                stringBuilder.append("Informamos que o valor relativo ao seu pedido ficará superior a 120% do valor orçamentado.\n");
                stringBuilder.append("Deseja prosseguir com a reparação ou rejeitar?\n");
                stringBuilder.append("Responda a este email caso queira prosseguir.\n");
                stringBuilder.append("Caso rejeite, ignore este email. E faça o levantamento do equipamento na loja\n");
                break;
        }
        stringBuilder.append("\nCumprimentos, SGCR.");
        return stringBuilder.toString();
    }


    public static void pedidoOrcamento(String email, String nome, double orcamento, Duration duration) {
        String message = mesageOrcamento(nome, orcamento, duration);
        String subject = subject(pedidoOrcamento);
        sendEmail(email, message, subject);
    }

    public static void naoPodeSerReparado(String email, String nome) {
        String message = message(naoPodeSerReparado, nome);
        String subject = subject(naoPodeSerReparado);
        sendEmail(email, message, subject);
    }

    public static void prontoALevantar(String email, String nome) {
        String message = message(prontoaLevantar, nome);
        String subject = subject(prontoaLevantar);
        sendEmail(email, message, subject);
    }

    public static void valorSuperiorOrcamento(String email, String nome) {
        String message = message(valorSuperiorOrcamento, nome);
        String subject = subject(valorSuperiorOrcamento);
        sendEmail(email, message, subject);
    }

    private static void sendEmail(String email, String messageText, String subject) {
        Session session = startSessionSend();
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);
            message.setText(messageText);
            //send the message
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();

        }
    }


    private static Session startSessionSend() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        return Session.getDefaultInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });
    }


    public static boolean checkRespostaPedidoOrcamento(String email) {
        return checkIfRespond(email, subject(pedidoOrcamento));
    }

    public static boolean checkRespostaValorSuperior(String email) {
        return checkIfRespond(email, subject(valorSuperiorOrcamento));
    }


    private Message[] getMessage() throws MessagingException {
        Properties properties = new Properties();
        //
        String host = "pop.gmail.com";
        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");
        Session emailSession = Session.getInstance(properties);

        //create the POP3 store object and connect with the pop server
        Store store = emailSession.getStore("pop3s");

        store.connect(host, user, password);

        //create the folder object and open it
        Folder emailFolder = store.getFolder("INBOX");
        emailFolder.open(Folder.READ_ONLY);

        // retrieve the messages from the folder in an array and print it
        return emailFolder.getMessages();
    }

    private static boolean checkIfRespond(String email, String subjectName) {
        try {
            Properties properties = new Properties();
            //
            String host = "pop.gmail.com";
            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect(host, user, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();


            for (Message message : messages) {
                Address address = message.getFrom()[0];
                String adressString = address.toString();
                adressString = adressString.substring(adressString.indexOf('<') + 1);
                adressString = adressString.substring(0, adressString.length() - 1);
                boolean emailBool = adressString.equals(email);
                if (emailBool) {
                    String subject = message.getSubject();
                    System.out.println("Email:" + adressString);
                    System.out.println("Subject: " + message.getSubject());
                    boolean subjectBool = subject.contains("Re: " + subjectName);
                    if (subjectBool) return true;
                }

            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    //https://www.tutorialspoint.com/javamail_api/javamail_api_deleting_emails.htm

    public static void main(String[] args) {
        String email = "jdmartinsvieira63@gmail.com";
        pedidoOrcamento(email,"Diogo",50,Duration.ofHours(37));
        //naoPodeSerReparado("jdmartinsvieira63@gmail.com","Diogo");
        //valorSuperiorOrcamento("jdmartinsvieira63@gmail.com","Diogo");
        //boolean resposta = checkRespostaPedidoOrcamento(email);

    }


    public void run() {
        while (running) {
            List<Map.Entry<String, String>> pedidosAguardarEmail = sgcr.listPedidosAguardaAceitacao();
            List<Map.Entry<String, String>> reparacoesAguardarEmail = sgcr.listReparacoesAguardaAceitacao();
            
            try {
                Message[] messagesArray = getMessage();
                for (Message message : messagesArray){
                    Address address = message.getFrom()[0];
                    String adressString = address.toString();
                    adressString = adressString.substring(adressString.indexOf('<') + 1);
                    adressString = adressString.substring(0, adressString.length() - 1);
                    String subject = message.getSubject();

                    for(Map.Entry<String,String> entry : pedidosAguardarEmail){
                        String email = entry.getValue();
                        String idPedido = entry.getKey();
                        if (email.equals(adressString)){
                            String subjectCompare = subject(pedidoOrcamento);
                            if (subject.equals("Re: "+ subjectCompare)){
                                sgcr.registaAceitacaoPlanoCliente(idPedido);
                            }
                        }
                    }

                    for (Map.Entry<String,String> entry : reparacoesAguardarEmail){
                        String reparacao = entry.getKey();
                        String email = entry.getValue();
                        if (email.equals(adressString)){
                            String subjectCompare = subject(valorSuperiorOrcamento);
                            if (subject.equals("Re: "+ subjectCompare)){
                                sgcr. registaAceitacaoReparacaoCliente(reparacao);
                            }
                        }
                    }
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    public void sendEmail (String email){

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.transport.protocol", "smtp");

        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("sgcr.SGCR - Mensagem");
            message.setText("Mensagem relativamente ao seu pedido na nossa loja..");

            //send the message
            Transport.send(message);

            System.out.println("message sent successfully...");

        } catch (MessagingException e) {
            e.printStackTrace();

            //check(host, mailStoreType, username, password);

        }
    }
     */
}
