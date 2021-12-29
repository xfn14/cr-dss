package gui.pedidos;

import gui.PrettyFrame;
import sgcr.SGCR;
import utils.gui.HintTextArea;
import utils.gui.HintTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CriaPlanoTrabalho extends PrettyFrame implements ActionListener {
    private final SGCR sgcr;
    private String idPedido;

    public CriaPlanoTrabalho(SGCR sgcr, String idPedido) {
        super("Cria Plano Trabalho", 350, 200);
        this.sgcr = sgcr;
        this.idPedido = idPedido;
    }

    @Override
    public void addComponents() {
    }

    @Override
    public void addActionListener() {
    }

    public void actionPerformed(ActionEvent e) {
    }

    public JPanel createPasso(){
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JTextField horas = new HintTextField("Horas");
        JTextField custo = new HintTextField("Custo");
        JTextArea descricao = new HintTextArea("Introduza uma descrição");
        JButton confirm = new JButton("Adicionar Passo");
        JTextField status = new JTextField("");
        confirm.addActionListener(e -> {
            try{
                double h = Double.parseDouble(horas.getText());
                double c = Double.parseDouble(custo.getText());
            }catch (NumberFormatException ex){
                status.setText("<html><font color=red>Input Invalido!</font></html>");
            }
        });

        panel.setLayout(layout);
        return panel;
    }
}
