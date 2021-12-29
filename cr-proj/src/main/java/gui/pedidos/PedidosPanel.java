package gui.pedidos;

import exceptions.SemTecnicosDisponiveis;
import sgcr.SGCR;
import trabalhadores.Trabalhador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedidosPanel extends JPanel implements ActionListener {
    private Logger logger = Logger.getLogger("CR");
    private SGCR sgcr;
    private Trabalhador trabalhador;

    private JButton list;
    private JButton registarPO;
    private JButton registarSE;

    public PedidosPanel(SGCR sgcr, Trabalhador trabalhador) {
        super(new GridBagLayout());
        this.sgcr = sgcr;
        this.trabalhador = trabalhador;

        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5);
        grid.fill = GridBagConstraints.HORIZONTAL;

        this.list = new JButton("Listar Pedidos");
        this.list.addActionListener(this);
        grid.gridx = 0;
        grid.gridy = 0;
        super.add(this.list, grid);

        this.registarPO = new JButton("Registar\nPedido Orcamento");
        this.registarPO.addActionListener(this);
        grid.gridx = 1;
        grid.gridy = 0;
        super.add(this.registarPO, grid);

        this.registarSE = new JButton("Registar\nServiço Expresso");
        this.registarSE.addActionListener(this);
        grid.gridx = 1;
        grid.gridy = 0;
        super.add(this.registarSE, grid);


        super.setBackground(new Color(0, 255, 0, 255));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.registarPO)) {
            RegistarPO registarPO = new RegistarPO(this.sgcr, this.trabalhador);
            registarPO.setAlwaysOnTop(true);
        } else if (e.getSource().equals(this.registarSE)) {
            Trabalhador tecnico = null;
            try {
                tecnico = this.sgcr.verificarDisponibilidadeTecnicos();
                RegistarSE registarSE = new RegistarSE(this.sgcr, this.trabalhador, tecnico);
                registarSE.setAlwaysOnTop(true);
            } catch (SemTecnicosDisponiveis semTecnicosDisponiveis) {
                this.logger.log(Level.INFO, "Sem tecnicos disponiveis");
                JOptionPane.showConfirmDialog(
                        new JFrame(),
                        "Sem tecnicos disponiveis",
                        "Registar Pedido Expresso",
                        JOptionPane.OK_CANCEL_OPTION
                );
            }

        } else if (e.getSource().equals(this.list)) {
            ListPedido listPedido = new ListPedido(this.sgcr);
            listPedido.setAlwaysOnTop(true);
        }
    }
}
