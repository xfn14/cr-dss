package emailHandler;

import sgcr.SGCR;
import utils.Constantes;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Email{
    private static final int pedidoOrcamento = 0;
    private static final int naoPodeSerReparado = 1;
    private static final int prontoaLevantar = 2;
    private static final int valorSuperiorOrcamento = 3;
    private static String password = "grupoG35";
    /*
        Mensages:
        - Equipamento não pode ser reparado
        - Equipamento pronto a ser Levantado
        - Valor da reparação superior 120% do valor orçamentado
        - Resposta plano Trabalho
     */
    private static String user = "sgcrgrupo35@gmail.com";
    private SGCR sgcr;
    private Timer timer;
    public Email(SGCR sgcr) {
        this.timer= new Timer();
        this.sgcr = sgcr;
    }

    private static String subject(int tipo) {
        String result = "";
        if (tipo == pedidoOrcamento) result = "Pedido de Orçamento";
        if (tipo == naoPodeSerReparado) result = "Equipamento Sem Reparação";
        if (tipo == prontoaLevantar) result = "Equipamento pronto a ser levantado";
        if (tipo == valorSuperiorOrcamento) result = "Valor Superior ao Orçamento";
        return "SGCR - " + result;
    }

    private static String mesageOrcamento(String nome, double orcamento, Duration duration) {
        long daysValue = duration.toDays();
        String days = daysValue == 0 ? "" : daysValue+ " dia(s) ";
        long hoursValue= duration.minusDays(daysValue).toHours();
        String hours = hoursValue == 0 ? "" : hoursValue + " horas";
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
        Duration newDuration = duration.dividedBy(Constantes.horasTrabalhoDia);
        String message = mesageOrcamento(nome, orcamento, newDuration);
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



    public static void main(String[] args) {
        String email = "jdmartinsvieira63@gmail.com";
        pedidoOrcamento(email, "Diogo", 50, Duration.ofHours(37));
        //naoPodeSerReparado("jdmartinsvieira63@gmail.com","Diogo");
        //valorSuperiorOrcamento("jdmartinsvieira63@gmail.com","Diogo");
        //boolean resposta = checkRespostaPedidoOrcamento(email);

    }


    //https://www.tutorialspoint.com/javamail_api/javamail_api_deleting_emails.htm

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

    public void cancelTimer (){
        this.timer.cancel();
    }


    private boolean passou30dias(LocalDateTime sent){
        return sent.plusDays(30).isBefore(LocalDateTime.now());
    }

    public void checkEmail(){
        int minutes = 5;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Map<String,Map.Entry<String, LocalDateTime>> pedidosAguardarEmail = sgcr.listPedidosAguardaAceitacao();
                Map<String,Map.Entry<String, LocalDateTime>> reparacoesAguardarEmail = sgcr.listReparacoesAguardaAceitacao();
                try {
                    Message[] messagesArray = getMessage();
                    for (Message message : messagesArray) {
                        Address address = message.getFrom()[0];
                        String adressString = address.toString();
                        adressString = adressString.substring(adressString.indexOf('<') + 1);
                        adressString = adressString.substring(0, adressString.length() - 1);
                        String subject = message.getSubject();

                        for (Map.Entry<String,Map.Entry<String,LocalDateTime>> entry : pedidosAguardarEmail.entrySet()) {
                            Map.Entry<String,LocalDateTime> emailDataEntry = entry.getValue();
                            String email = emailDataEntry.getKey();
                            String idPedido = entry.getKey();
                            if (email.equals(adressString)) {
                                String subjectCompare = subject(pedidoOrcamento);
                                if (subject.equals("Re: " + subjectCompare)) {
                                    sgcr.registaAceitacaoPlanoCliente(idPedido);
                                    continue;
                                }
                            }
                            LocalDateTime dataContacto =emailDataEntry.getValue();
                            if (passou30dias(dataContacto)){
                                sgcr.arquivarPedido(idPedido);
                            }


                        }

                        for (Map.Entry<String,Map.Entry<String,LocalDateTime>> entry : reparacoesAguardarEmail.entrySet()) {
                            Map.Entry<String,LocalDateTime> emailDataEntry = entry.getValue();
                            String email = emailDataEntry.getKey();
                            String idPedido = entry.getKey();
                            if (email.equals(adressString)) {
                                String subjectCompare = subject(valorSuperiorOrcamento);
                                if (subject.equals("Re: " + subjectCompare)) {
                                    sgcr.registaAceitacaoReparacaoCliente(idPedido);
                                    continue;
                                }
                            }
                            LocalDateTime dataContacto =emailDataEntry.getValue();
                            if (passou30dias(dataContacto)){
                                sgcr.arquivarPedido(idPedido);
                            }
                        }
                    }
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

                    }
        }, 0, minutes * 60 * 1000);
    }
}
