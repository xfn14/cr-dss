package gui.tecnico;

import exceptions.InvalidIdException;
import gui.PrettyFrame;
import sgcr.SGCR;
import utils.gui.HintTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConcluirSE extends PrettyFrame implements ActionListener {
    private final SGCR sgcr;

    private JTextField idPedido;
    private JButton concluir;

    public ConcluirSE(SGCR sgcr) {
        super("Concluir Serviço Expresso", 300, 150);
        this.sgcr = sgcr;
    }

    @Override
    public void addComponents() {
        this.idPedido = new HintTextField("ID Pedido");
        this.concluir = new JButton("Concluir");

        this.addComponent(new JLabel("ID Pedido"), 0, 0, 1, 1);
        this.addComponent(this.idPedido,0, 1, 1, 1);
        this.addComponent(this.concluir, 1, 0, 2, 1);
    }

    @Override
    public void addActionListener() {
        this.idPedido.addActionListener(this);
        this.concluir.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this.concluir)){
            try {
                this.sgcr.terminaServicoExpresso(this.idPedido.getText());
                super.dispose();
            } catch (InvalidIdException ex) {
                JOptionPane.showConfirmDialog(
                        new JFrame(),
                        "Id de serviço expresso invalido",
                        "Concluir Serviço Expresso",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
            }
        }
    }
}
