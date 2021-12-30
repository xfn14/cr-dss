package gui.pedidos;

import gui.PrettyFrame;
import sgcr.SGCR;
import trabalhadores.Trabalhador;
import utils.gui.HintTextArea;
import utils.gui.HintTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistarPO extends PrettyFrame implements ActionListener {
    private final SGCR sgcr;
    private final Trabalhador trabalhador;

    private JTextField cliente;
    private JTextArea descricao;
    private JButton confirm;
    private JLabel status;

    public RegistarPO(SGCR sgcr, Trabalhador trabalhador) {
        super("Registar Pedido", 700, 500);
        this.sgcr = sgcr;
        this.trabalhador = trabalhador;
    }

    @Override
    public void addComponents() {
        this.cliente = new HintTextField("NIF do Cliente");
        this.descricao = new HintTextArea("Explique o pedido do cliente");
        JScrollPane scroll = new JScrollPane(this.descricao);
        scroll.setPreferredSize(new Dimension(200, 70));
        this.confirm = new JButton("Registar");
        this.status = new JLabel("");

        super.addComponent(new JLabel("Cliente"), 0, 0, 1, 1);
        super.addComponent(this.cliente, 0, 1, 1, 1);
        super.addComponent(new JLabel("Descrição"), 1, 0, 1, 1);
        super.addComponent(scroll, 1, 1, 1, 1);
        super.addComponent(this.confirm, 2, 1, 2, 1);
        super.addComponent(this.status, 3, 1, 2, 1);
    }

    @Override
    public void addActionListener() {
        this.confirm.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.confirm)) {
            if (this.descricao.getText().isBlank()) {
                this.status.setText("<html><font color=red>Descrição Obrigatória!</font></html>");
            } else if (this.sgcr.isClienteAutenticado(this.cliente.getText())) {
                this.sgcr.registaPedidoOrcamento(
                        this.cliente.getText(),
                        this.trabalhador.getIdTrabalhador(),
                        this.descricao.getText()
                );
                this.status.setText("");
                this.dispose();
            } else {
                this.status.setText("<html><font color=red>Cliente Inválido!</font></html>");
            }
        }
    }
}
