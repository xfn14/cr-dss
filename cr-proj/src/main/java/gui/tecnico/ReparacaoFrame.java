package gui.tecnico;

import exceptions.InvalidIdException;
import exceptions.ValorSuperior;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReparacaoFrame extends PrettyFrame implements ActionListener, Runnable {
    private final Logger logger = Logger.getLogger("CR");
    private final SGCR sgcr;
    private final String idReparacao;
    private final JTextArea currentPasso;
    private final JLabel currentTime;
    //    private final JScrollPane scrollPane;
    private JTextField horasInput;
    private JTextField custoInput;
    private JTextField info;
    private JButton pausaRetomar;
    private JButton finalizarPasso;
    private java.util.Timer timer;
    private int current, total;

    public ReparacaoFrame(SGCR sgcr, String idReparacao, String idTecnico) {
        super("Reparação | ID: " + idReparacao, 1200, 900);
        this.sgcr = sgcr;
        this.idReparacao = idReparacao;

        this.timer = new java.util.Timer();

        this.sgcr.iniciaReparacao(idReparacao, idTecnico);

        this.currentPasso = new JTextArea();
        this.setCurrentPasso();
        this.currentPasso.setLineWrap(true);
        this.currentPasso.setWrapStyleWord(true);
        this.currentPasso.setEditable(false);
        this.currentPasso.setSize(new Dimension(600, 300));
//        this.scrollPane = new JScrollPane(this.currentPasso);
//        this.scrollPane.setPreferredSize(new Dimension(600, 300));
        super.addComponent(this.currentPasso, 0, 0, 2, 1);

        this.currentTime = new JLabel(currentTime());

        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (current + 1 <= total) {
                    sgcr.reparacaoParaEsperaTempo(idReparacao);
                    System.out.println("oi");
                }
            }
        });
    }

    @Override
    public void addComponents() {
        this.horasInput = new HintTextField("Tempo do passo atual");
        this.custoInput = new HintTextField("Custo do passo atual");
        this.info = new HintTextField("Informação sobre o que foi feito");
        this.pausaRetomar = new JButton("Pausa");
        this.finalizarPasso = new JButton("Finalizar Passo");

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
                this.sgcr.reparacaoParaEsperaPecas(this.idReparacao);
            } else {
                this.pausaRetomar.setText("Pausa");
                this.sgcr.reparacaoParaDecorrer(this.idReparacao);
            }
        } else if (e.getSource().equals(this.finalizarPasso) && this.pausaRetomar.getText().equals("Pausa")) {
            if (this.horasInput.getText().isBlank() || this.custoInput.getText().isBlank()) {
                this.inputInvalido();
            } else {
                try {
                    int h = Integer.parseInt(this.horasInput.getText());
                    int c = Integer.parseInt(this.custoInput.getText());
                    if (h < 0 || c < 0) {
                        this.inputInvalido();
                        return;
                    }
                    try {
                        if (this.current + 1 == total) {
                            this.sgcr.registaPasso(h, c, this.info.getText(), this.idReparacao);
                            this.clearFields();
                            try {
                                this.sgcr.registaConclusaoReparacao(this.idReparacao);
                                this.current++;
                                this.dispose();
                            } catch (InvalidIdException ex) {
                                this.logger.log(Level.SEVERE, "Id de reparação invalido.");
                            }
                        } else {
                            this.setCurrentPasso();
                        }
                    } catch (ValorSuperior ex) {
                        JOptionPane.showConfirmDialog(
                                new JFrame(),
                                "Orçamento excedido, aguarde resposta do cliente",
                                "Reparação",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.PLAIN_MESSAGE
                        );
                        super.dispose();
                    }
                } catch (NumberFormatException ex) {
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

    private void setCurrentPasso() {
        String desc = this.sgcr.getPassoAtualDescricao(this.idReparacao);
        this.currentPasso.setText(desc == null ? "" : desc);
        this.current = this.sgcr.getPassoAtualIndex(this.idReparacao);
        this.total = this.sgcr.getTotalPassos(this.idReparacao);
        System.out.println(this.current + " " + this.total);
    }

    private void clearFields() {
        this.horasInput.setText("");
        this.custoInput.setText("");
        this.info.setText("");
    }

    private void inputInvalido() {
        JOptionPane.showConfirmDialog(
                new JFrame(),
                "Input Invalido! Por favor preencha todas as caixas.",
                "Retomar Reparações",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
    }
}
