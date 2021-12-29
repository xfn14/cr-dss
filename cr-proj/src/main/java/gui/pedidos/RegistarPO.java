package gui.pedidos;

import sgcr.SGCR;
import trabalhadores.Trabalhador;
import utils.gui.HintTextArea;
import utils.gui.HintTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistarPO extends JFrame implements ActionListener {
    private final SGCR sgcr;

    private JPanel panel;
    private JTextField cliente;
    private Trabalhador trabalhador;
    private JTextArea descricao;
    private JButton confirm;
    private JLabel status;

    public RegistarPO(SGCR sgcr, Trabalhador trabalhador) {
        super("Registar Pedido");
        this.sgcr = sgcr;
        this.trabalhador = trabalhador;

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

        JLabel cliente = new JLabel("Cliente");
        grid.gridx = 0;
        grid.gridy = 0;
        this.panel.add(cliente, grid);

        this.cliente = new HintTextField("NIF do Cliente");
        grid.gridx = 1;
        grid.gridy = 0;
        this.panel.add(this.cliente, grid);

        JLabel descricao = new JLabel("Descrição");
        grid.gridx = 0;
        grid.gridy = 1;
        this.panel.add(descricao, grid);

        this.descricao = new HintTextArea("Explique o pedido do cliente");
        grid.gridx = 1;
        grid.gridy = 1;
        grid.gridwidth = 10;
        grid.gridheight = 5;
        this.panel.add(this.descricao, grid);

        this.confirm = new JButton("Registar");
        this.confirm.addActionListener(this);
        grid.gridx = 0;
        grid.gridy = 6;
        grid.gridwidth = 2;
        grid.gridheight = 1;
        this.panel.add(this.confirm, grid);

        this.status = new JLabel("");
        grid.gridx = 0;
        grid.gridy = 7;
        grid.gridwidth = 10;
        this.panel.add(this.status, grid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.confirm)) {
            if (this.sgcr.getPedidos().getClientesId().contains(this.cliente.getText())) {
                this.sgcr.registaPedidoOrcamento(
                        this.cliente.getText(),
                        this.trabalhador.getIdTrabalhador(),
                        this.descricao.getText()
                );
                this.status.setText("");
                this.dispose();
            } else {
                this.status.setText("<html><font color=red>Cliente Invalido!</font></html>");
            }
        }
    }
}
