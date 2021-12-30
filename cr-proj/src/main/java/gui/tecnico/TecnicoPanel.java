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

        super.setBackground(new Color(255, 100, 100, 255));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.planoTrabalho)) {
            try {
                // TODO Ao criar plano de trabalho tem de ser atualizado o idPlanoTrabalho no Pedido em si
                String idPedido = this.sgcr.getPedidoOrcamentoMaisAntigo();
                new CriaPlanoTrabalho(this.sgcr, idPedido, this.trabalhador.getIdTrabalhador());
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
                // TODO o metodo getReparacaoMaisUrgente() escolhe uma reparaçao para o
                // tecnico fazer se ainda n tiver sido iniciada, inicia a reparacao o plano etc
                // qnd o cliente fecha a janela a reparacao fica em pausa e o tecnico volta a estar
                // disponivel
                String idReparacao = this.sgcr.getReparacaoMaisUrgente(this.trabalhador.getIdTrabalhador());
                ReparacaoFrame reparacaoFrame = new ReparacaoFrame(this.sgcr, idReparacao);
                Thread repracaoThread = new Thread(reparacaoFrame);
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
        }
    }
}
