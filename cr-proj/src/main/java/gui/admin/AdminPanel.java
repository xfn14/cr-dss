package gui.admin;

import sgcr.SGCR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel extends JPanel implements ActionListener {
    private SGCR sgcr;

    private JButton list;
    private JButton registar;

    public AdminPanel(SGCR sgcr) {
        super(new GridBagLayout());
        this.sgcr = sgcr;

        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5);
        grid.fill = GridBagConstraints.HORIZONTAL;

        this.registar = new JButton("Registar Trabalhador");
        this.registar.addActionListener(this);
        grid.gridx = 0;
        grid.gridy = 0;
        super.add(this.registar, grid);

        this.list = new JButton("Listar Trabalhadores");
        this.list.addActionListener(this);
        grid.gridx = 1;
        grid.gridy = 0;
        super.add(this.list, grid);

        super.setBackground(new Color(255, 255, 0, 255));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.registar)) {
            new RegistarTrabalhador(this.sgcr);
        } else if (e.getSource().equals(this.list)) {
            new ListTrabalhadores(this.sgcr);
        }
    }
}
