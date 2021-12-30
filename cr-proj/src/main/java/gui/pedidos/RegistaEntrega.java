package gui.pedidos;

import gui.PrettyFrame;
import sgcr.SGCR;
import utils.gui.HintTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistaEntrega extends PrettyFrame implements ActionListener {
    private final SGCR sgcr;
    private final String idTrabalhador;

    private JTextField idPedido;
    private JButton confirm;

    public RegistaEntrega(SGCR sgcr, String idTrabalhador) {
        super("Cancelar Pedido", 420, 200);
        this.sgcr = sgcr;
        this.idTrabalhador = idTrabalhador;
    }

    @Override
    public void addComponents() {
        this.idPedido = new HintTextField("ID Pedido");
        this.confirm = new JButton("Registar Entrega");

        this.addComponent(new JLabel("ID Pedido"), 0, 0, 1, 1);
        this.addComponent(this.idPedido,0, 1, 1, 1);
        this.addComponent(this.confirm, 1, 0, 2, 1);
    }

    @Override
    public void addActionListener() {
        this.idPedido.addActionListener(this);
        this.confirm.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this.confirm)){
            if(this.sgcr.isValidID(this.idPedido.getText())){
                this.sgcr.entregaEquipamento(this.idPedido.getText(), this.idTrabalhador);
                super.dispose();
            }else{
                JOptionPane.showConfirmDialog(
                        new JFrame(),
                        "Id de pedido invalido",
                        "Registar Entrega",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
            }
        }
    }
}
