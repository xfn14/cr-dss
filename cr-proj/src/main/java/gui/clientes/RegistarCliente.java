package gui.clientes;

import exceptions.JaExisteException;
import gui.PrettyFrame;
import sgcr.SGCR;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistarCliente extends PrettyFrame implements ActionListener {
    private final SGCR sgcr;

    private JTextField nomeInput;
    private JTextField nifInput;
    private JTextField numeroInput;
    private JTextField emailInput;
    private JButton confirm;

    public RegistarCliente(SGCR sgcr) {
        super("Registar Cliente", 700, 500);
        this.sgcr = sgcr;
    }

    public void addComponents() {
        this.nomeInput = new JTextField(16);
        this.nifInput = new JTextField(16);
        this.numeroInput = new JTextField(16);
        this.emailInput = new JTextField(16);
        this.confirm = new JButton("Registar");


        super.addComponent(new JLabel("Nome"), 0, 0);
        super.addComponent(this.nomeInput, 0, 1);
        super.addComponent(new JLabel("NIF"), 1, 0);
        super.addComponent(this.nifInput, 1, 1);
        super.addComponent(new JLabel("Numero"), 2, 0);
        super.addComponent(this.numeroInput, 2, 1);
        super.addComponent(new JLabel("Email"), 3, 0);
        super.addComponent(this.emailInput, 3, 1);
        super.addComponent(this.confirm, 4, 1, 2, 1);
    }

    public void addActionListener() {
        this.confirm.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.confirm)) {
            if (!this.nifInput.getText().isBlank()) {
                try {
                    this.sgcr.criarFichaCliente(
                            this.nomeInput.getText(),
                            this.emailInput.getText(),
                            this.numeroInput.getText(),
                            this.nifInput.getText()
                    );
                } catch (JaExisteException exception) {
                    String exceptionMessage = exception.getMessage();
                    JOptionPane.showConfirmDialog(
                            new JFrame(),
                            exceptionMessage,
                            "NIF em uso",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.PLAIN_MESSAGE
                    );

                }
                super.dispose();
            } else {
                JOptionPane.showConfirmDialog(
                        new JFrame(),
                        "NIF obrigatório",
                        "Registar Cliente",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
            }
        }
    }
}
