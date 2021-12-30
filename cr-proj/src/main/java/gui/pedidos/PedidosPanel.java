package gui.pedidos;

import exceptions.SemTecnicosDisponiveis;
import sgcr.SGCR;
import trabalhadores.Funcionario;
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

    private JButton listPO;
    private JButton listSE;
    private JButton registarPO;
    private JButton registarSE;
    private JButton cancelarPedido;
    private JButton registaEntrega;

    public PedidosPanel(SGCR sgcr, Trabalhador trabalhador) {
        super(new GridBagLayout());
        this.sgcr = sgcr;
        this.trabalhador = trabalhador;

        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5);
        grid.fill = GridBagConstraints.HORIZONTAL;

        this.listPO = new JButton("Listar Pedidos Orcamento");
        this.listPO.addActionListener(this);
        grid.gridx = 0;
        grid.gridy = 0;
        super.add(this.listPO, grid);

        this.listSE = new JButton("Listar Serviços Expresso");
        this.listSE.addActionListener(this);
        grid.gridx = 1;
        grid.gridy = 0;
        super.add(this.listSE, grid);

        this.registarPO = new JButton("Registar Pedido Orcamento");
        this.registarPO.addActionListener(this);
        grid.gridx = 0;
        grid.gridy = 1;
        super.add(this.registarPO, grid);

        this.registarSE = new JButton("Registar Serviço Expresso");
        this.registarSE.addActionListener(this);
        grid.gridx = 1;
        grid.gridy = 1;
        super.add(this.registarSE, grid);

        this.cancelarPedido = new JButton("Cancelar Pedido");
        this.cancelarPedido.addActionListener(this);
        grid.gridx = 0;
        grid.gridy = 2;
        super.add(this.cancelarPedido, grid);

        if(this.trabalhador instanceof Funcionario){
            this.registaEntrega = new JButton("Regista Entrega");
            this.registaEntrega.addActionListener(this);
            grid.gridx = 1;
            grid.gridy = 2;
            super.add(this.registaEntrega, grid);
        }

//        super.setBackground(new Color(0, 255, 0, 255));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.registarPO)) {
            new RegistarPO(this.sgcr, this.trabalhador);
        } else if (e.getSource().equals(this.registarSE)) {
            try {
                Trabalhador tecnico = this.sgcr.verificarDisponibilidadeTecnicos();
                new RegistarSE(this.sgcr, this.trabalhador, tecnico);
            } catch (SemTecnicosDisponiveis semTecnicosDisponiveis) {
                this.logger.log(Level.INFO, "Sem tecnicos disponiveis");
                JOptionPane.showConfirmDialog(
                        new JFrame(),
                        "Sem tecnicos disponiveis",
                        "Registar Pedido Expresso",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
            }

        } else if (e.getSource().equals(this.listPO)) {
            new ListPO(this.sgcr);
        } else if(e.getSource().equals(this.cancelarPedido)){
            new CancelaPedido(this.sgcr);
        } else if(e.getSource().equals(this.listSE)){
            new ListSE(this.sgcr);
        } else if(e.getSource().equals(this.registaEntrega)){
            new RegistaEntrega(this.sgcr, this.trabalhador.getIdTrabalhador());
        }
    }
}
