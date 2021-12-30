package gui.tecnico;

import exceptions.InvalidIdException;
import gui.PrettyFrame;
import sgcr.SGCR;
import utils.gui.HintTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReparacaoFrame extends PrettyFrame implements ActionListener, Runnable {
    private final SGCR sgcr;
    private final String idReparacao;
    private final JTextArea currentPasso;
    private final JScrollPane scrollPane;
    private final JTextField horasInput;
    private final JTextField custoInput;
    private final JTextField info;
    private final JButton pausaRetomar;
    private final JButton finalizarPasso;
    private final JLabel currentTime;
    private java.util.Timer timer;
    private int current, total;

    public ReparacaoFrame(SGCR sgcr, String idReparacao) {
        super("Reparação | ID: " + idReparacao, 1200, 900);
        this.sgcr = sgcr;
        this.idReparacao = idReparacao;

        this.timer = new java.util.Timer();

        this.sgcr.reparacaoParaDecorrer(idReparacao);

        this.currentPasso = new JTextArea();
        this.setCurrentPasso();
        this.currentPasso.setLineWrap(true);
        this.currentPasso.setWrapStyleWord(true);
        this.currentPasso.setEditable(false);
        this.currentPasso.setOpaque(false);
        this.scrollPane = new JScrollPane(this.currentPasso);
        this.scrollPane.setPreferredSize(new Dimension(600, 300));
        this.scrollPane.setOpaque(false);

        this.horasInput = new HintTextField("Tempo do passo atual");
        this.custoInput = new HintTextField("Custo do passo atual");
        this.info = new HintTextField("Informação sobre o que foi feito");
        this.pausaRetomar = new JButton("Pausa");
        this.finalizarPasso = new JButton("Finalizar Passo");
        this.currentTime = new JLabel(currentTime());

        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                // TODO Mete em espera quando sai
                if(current + 1 != total)
                    sgcr.reparacaoParaEspera(idReparacao);
            }
        });
    }

    @Override
    public void addComponents() {
        super.addComponent(this.currentPasso, 0, 0, 2, 1);
        super.addComponent(new JLabel("Horas"), 1, 0);
        super.addComponent(this.horasInput, 1, 1);
        super.addComponent(new JLabel("Custo"), 2, 0);
        super.addComponent(this.custoInput, 2, 1);
        super.addComponent(new JLabel("Info"), 3, 0);
        super.addComponent(this.info, 3, 1);
        super.addComponent(this.pausaRetomar, 4, 0);
        super.addComponent(this.finalizarPasso, 4, 1);
    }

    @Override
    public void addActionListener() {
        this.pausaRetomar.addActionListener(this);
        this.finalizarPasso.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.pausaRetomar)) {
            if (this.pausaRetomar.getText().equals("Pausa")) {
                this.pausaRetomar.setText("Retomar");
                this.sgcr.reparacaoParaEspera(this.idReparacao);
            } else {
                this.pausaRetomar.setText("Pausa");
                this.sgcr.reparacaoParaDecorrer(this.idReparacao);
            }
        } else if (e.getSource().equals(this.finalizarPasso) && this.pausaRetomar.getText().equals("Pausa")) {
            if(this.horasInput.getText().isBlank() || this.custoInput.getText().isBlank()){
                this.inputInvalido();
            }else{
                try{
                    int h = Integer.parseInt(this.horasInput.getText());
                    int c = Integer.parseInt(this.custoInput.getText());
                    if(h < 0 || c < 0){
                        this.inputInvalido();
                        return;
                    }
                    this.sgcr.addPasso(this.idReparacao, h, c, this.info.getText());
                    if(this.current + 1 == total){
                        this.sgcr.conclusaoReparacao(this.idReparacao);
                    }else{
                        this.setCurrentPasso();
                    }
                }catch (NumberFormatException ex){
                    this.inputInvalido();
                }
            }
        }
    }

    @Override
    public void run() {
        int minutes = 1;
        timer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                currentTime.setText(currentTime());
            }
        }, 3000, minutes * 60 * 1000);
    }

    private String currentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    private void setCurrentPasso(){
        this.currentPasso.setText(this.sgcr.getPassoAtualDescricao(this.idReparacao));
        this.current = this.sgcr.getPassoAtualIndex(this.idReparacao);
        this.total = this.sgcr.getTotalPassos(this.idReparacao);
    }

    private void clearFields(){
        this.horasInput.setText("");
        this.custoInput.setText("");
        this.info.setText("");
    }

    private void inputInvalido(){
        JOptionPane.showConfirmDialog(
                new JFrame(),
                "Input Invalido! Por favor preencha todas as caixas.",
                "Retomar Reparações",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
    }
}
