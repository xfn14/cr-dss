package gui.clientes;

import sgcr.SGCR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistarCliente extends JFrame implements ActionListener {
    private final SGCR sgcr;

    private JPanel panel;
    private JTextField nomeInput;
    private JTextField nifInput;
    private JTextField numeroInput;
    private JTextField emailInput;
    private JButton confirm;

    public RegistarCliente(SGCR sgcr) {
        super("Registar Funcionario");
        this.sgcr = sgcr;

        this.initPanel();
        super.add(this.panel, BorderLayout.CENTER);

        super.setSize(700, 500);
        super.setResizable(false);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        super.setVisible(true);
    }

    private void initPanel() {
        this.panel = new JPanel(new GridBagLayout());

        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5);
        grid.fill = GridBagConstraints.HORIZONTAL;

        JLabel nome = new JLabel("Nome");
        grid.gridx = 0;
        grid.gridy = 0;
        this.panel.add(nome, grid);

        this.nomeInput = new JTextField(16);
        grid.gridx = 1;
        grid.gridy = 0;
        this.panel.add(this.nomeInput, grid);

        JLabel nif = new JLabel("NIF");
        grid.gridx = 0;
        grid.gridy = 1;
        this.panel.add(nif, grid);

        this.nifInput = new JTextField(16);
        grid.gridx = 1;
        grid.gridy = 1;
        this.panel.add(this.nifInput, grid);

        JLabel numero = new JLabel("Numero");
        grid.gridx = 0;
        grid.gridy = 2;
        this.panel.add(numero, grid);

        this.numeroInput = new JTextField(16);
        grid.gridx = 1;
        grid.gridy = 2;
        this.panel.add(this.numeroInput, grid);

        JLabel email = new JLabel("Email");
        grid.gridx = 0;
        grid.gridy = 3;
        this.panel.add(email, grid);

        this.emailInput = new JTextField(16);
        grid.gridx = 1;
        grid.gridy = 3;
        this.panel.add(this.emailInput, grid);

        this.confirm = new JButton("Registar");
        this.confirm.addActionListener(this);
        grid.gridwidth = 2;
        grid.gridx = 0;
        grid.gridy = 4;
        this.panel.add(this.confirm, grid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.confirm)) {
            this.sgcr.criarFichaCliente(
                    this.nomeInput.getText(),
                    this.emailInput.getText(),
                    this.numeroInput.getText(),
                    this.nifInput.getText()
            );
            super.dispose();
        }
    }
}
