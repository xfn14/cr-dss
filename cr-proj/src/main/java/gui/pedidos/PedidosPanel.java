package gui.pedidos;

import exceptions.SemPedidosOrcamento;
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
    private JButton cancelarPedido;
    private JButton createPlano;

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

        this.registarPO = new JButton("Registar Pedido Orcamento");
        this.registarPO.addActionListener(this);
        grid.gridx = 1;
        grid.gridy = 0;
        super.add(this.registarPO, grid);

        this.registarSE = new JButton("Registar Serviço Expresso");
        this.registarSE.addActionListener(this);
        grid.gridx = 0;
        grid.gridy = 1;
        super.add(this.registarSE, grid);

        this.cancelarPedido = new JButton("Cancelar Pedido");
        this.cancelarPedido.addActionListener(this);
        grid.gridx = 1;
        grid.gridy = 1;
        super.add(this.cancelarPedido, grid);

        this.createPlano = new JButton("Cria Plano Trabalho");
        this.createPlano.addActionListener(this);
        grid.gridx = 0;
        grid.gridy = 2;
        super.add(this.createPlano, grid);

        super.setBackground(new Color(0, 255, 0, 255));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.registarPO)) {
            RegistarPO registarPO = new RegistarPO(this.sgcr, this.trabalhador);
            registarPO.setAlwaysOnTop(true);
        } else if (e.getSource().equals(this.registarSE)) {
            Trabalhador tecnico;
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
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
            }

        } else if (e.getSource().equals(this.list)) {
            ListPedido listPedido = new ListPedido(this.sgcr);
            listPedido.setAlwaysOnTop(true);
        } else if(e.getSource().equals(this.cancelarPedido)){
            CancelaPedido cancelaPedido = new CancelaPedido(this.sgcr);
            cancelaPedido.setAlwaysOnTop(true);
        }else if(e.getSource().equals(this.createPlano)){
            try {
                String idPedido = this.sgcr.getPedidoOrcamentoMaisAntigo();
                CriaPlanoTrabalho criaPlanoTrabalho = new CriaPlanoTrabalho(this.sgcr, idPedido);
                criaPlanoTrabalho.setAlwaysOnTop(true);
            } catch (SemPedidosOrcamento ex) {
                this.logger.log(Level.INFO, "Sem tecnicos disponiveis");
                JOptionPane.showConfirmDialog(
                        new JFrame(),
                        "Nao existes pedidos de orçamento",
                        "Criar Plano de Trabalho",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
            }
        }
    }
}
