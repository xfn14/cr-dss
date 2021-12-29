package gui.pedidos;

import exceptions.InvalidIdException;
import gui.PrettyFrame;
import sgcr.SGCR;
import utils.gui.HintTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CancelaPedido extends PrettyFrame implements ActionListener {
    private final SGCR sgcr;

    private JTextField idPedido;
    private JButton cancelar;

    public CancelaPedido(SGCR sgcr) {
        super("Cancelar Pedido", 300, 150);
        this.sgcr = sgcr;
    }

    @Override
    public void addComponents() {
        this.idPedido = new HintTextField("ID Pedido");
        this.cancelar = new JButton("Cancelar");

        this.addComponent(new JLabel("ID Pedido"), 0, 0, 1, 1);
        this.addComponent(this.idPedido,0, 1, 1, 1);
        this.addComponent(this.cancelar, 1, 0, 2, 1);
    }

    @Override
    public void addActionListener() {
        this.idPedido.addActionListener(this);
        this.cancelar.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this.cancelar)){
            try {
                this.sgcr.cancelaPedido(this.idPedido.getText());
                super.dispose();
            } catch (InvalidIdException ex) {
                JOptionPane.showConfirmDialog(
                        new JFrame(),
                        "Id de pedido invalido",
                        "Cancelar pedido",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
            }
        }
    }
}
