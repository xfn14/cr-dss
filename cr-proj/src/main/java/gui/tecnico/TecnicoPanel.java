package gui.tecnico;

import exceptions.SemPedidosOrcamento;
import exceptions.SemReparacoesException;
import sgcr.SGCR;
import trabalhadores.Trabalhador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Thread;

public class TecnicoPanel extends JPanel implements ActionListener {
    private Logger logger = Logger.getLogger("CR");
    private SGCR sgcr;
    private Trabalhador trabalhador;

    private JButton planoTrabalho;
    private JButton reparar;
    private JButton concluirSE;

    public TecnicoPanel(SGCR sgcr, Trabalhador trabalhador) {
        super(new GridBagLayout());
        this.sgcr = sgcr;
        this.trabalhador = trabalhador;

        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5);
        grid.fill = GridBagConstraints.HORIZONTAL;

        this.planoTrabalho = new JButton("Criar Plano Trabalho");
        this.planoTrabalho.addActionListener(this);
        grid.gridx = 0;
        grid.gridy = 0;
        super.add(this.planoTrabalho, grid);

        this.reparar = new JButton("Reparar Equipamento");
        this.reparar.addActionListener(this);
        grid.gridx = 0;
        grid.gridy = 1;
        super.add(this.reparar, grid);

        this.concluirSE = new JButton("Concluir Serviço Expresso");
        this.concluirSE.addActionListener(this);
        grid.gridx = 0;
        grid.gridy = 2;
        super.add(this.concluirSE, grid);

//        super.setBackground(new Color(255, 100, 100, 255));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.planoTrabalho)) {
            try {
                int option = JOptionPane.showConfirmDialog(
                        new JFrame(),
                        "Quer dividir em sub-passos?",
                        "Criar Passo",
                        JOptionPane.YES_NO_OPTION
                );
                String idPedido = this.sgcr.getPedidoOrcamentoMaisAntigo();
                new CriaPlanoTrabalho(this.sgcr, idPedido, this.trabalhador.getIdTrabalhador(), option == 0);
            } catch (SemPedidosOrcamento ex) {
                this.logger.log(Level.INFO, "Não existem pedidos de orçamento");
                JOptionPane.showConfirmDialog(
                        new JFrame(),
                        "Nao existes pedidos de orçamento",
                        "Criar Plano de Trabalho",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
            }
        } else if (e.getSource().equals(this.reparar)) {
            try {
                String idReparacao = this.sgcr.getReparacaoMaisUrgente(this.trabalhador.getIdTrabalhador());
                ReparacaoFrame reparacaoFrame = new ReparacaoFrame(this.sgcr, idReparacao, this.trabalhador.getIdTrabalhador());
                Thread reparacaoThread = new Thread(reparacaoFrame);
                reparacaoThread.start();
            } catch (SemReparacoesException ex) {
                this.logger.log(Level.INFO, "Não existem reparaçoes");
                JOptionPane.showConfirmDialog(
                        new JFrame(),
                        "Nao existes reparações urgentes",
                        "Retomar Reparações",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
            }
        } else if(e.getSource().equals(this.concluirSE)){
            new ConcluirSE(this.sgcr);
        }
    }
}
