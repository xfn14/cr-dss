package gui.pedidos;

import sgcr.SGCR;
import trabalhadores.Trabalhador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PedidosPanel extends JPanel implements ActionListener {
    private SGCR sgcr;
    private Trabalhador trabalhador;

    private JButton list;
    private JButton registar;

    public PedidosPanel(SGCR sgcr, Trabalhador trabalhador) {
        super(new GridBagLayout());
        this.sgcr = sgcr;
        this.trabalhador = trabalhador;

        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5);
        grid.fill = GridBagConstraints.HORIZONTAL;

        this.registar = new JButton("Registar Pedido");
        this.registar.addActionListener(this);
        grid.gridx = 0;
        grid.gridy = 0;
        super.add(this.registar, grid);

        this.list = new JButton("Listar Pedidos");
        this.list.addActionListener(this);
        grid.gridx = 1;
        grid.gridy = 0;
        super.add(this.list, grid);

        super.setBackground(new Color(0, 255, 0, 255));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.registar)) {
            RegistarPO registarPO = new RegistarPO(this.sgcr, this.trabalhador);
            registarPO.setAlwaysOnTop(true);
        } else if (e.getSource().equals(this.list)) {
            ListPedido listPedido = new ListPedido(this.sgcr);
            listPedido.setAlwaysOnTop(true);
        }
    }
}
