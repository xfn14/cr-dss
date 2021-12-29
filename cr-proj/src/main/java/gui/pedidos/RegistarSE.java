package gui.pedidos;

import pedidos.ServicoExpresso;
import sgcr.SGCR;
import trabalhadores.Trabalhador;
import utils.gui.HintTextArea;
import utils.gui.HintTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.stream.Collectors;

public class RegistarSE extends JFrame implements ActionListener {
    private final SGCR sgcr;

    private JPanel panel;
    private JComboBox<String> tipo;
    private JTextField idCliente;
    private final Trabalhador trabalhador;
    private JTextField idTecnico;
    private JTextArea descricao;
    private JButton confirm;
    private JLabel status;

    public RegistarSE(SGCR sgcr, Trabalhador trabalhador, Trabalhador tecnico) {
        super("Registar Pedido");
        this.sgcr = sgcr;
        this.trabalhador = trabalhador;

        this.initPanel();
        super.add(this.panel, BorderLayout.CENTER);

        super.setSize(700, 500);
        super.setResizable(false);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        super.setVisible(true);
    }

    private void initPanel() {
        this.panel = new JPanel(new GridBagLayout());

        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5);
        grid.fill = GridBagConstraints.HORIZONTAL;

        JLabel tipo = new JLabel("Tipo");
        grid.gridx = 0;
        grid.gridy = 0;
        this.panel.add(tipo, grid);

        String[] tipos = Arrays.stream(ServicoExpresso.Tipo.values())
                .map(ServicoExpresso.Tipo::toString)
                .toArray(String[]::new);
        this.tipo = new JComboBox<>(tipos);
        this.tipo.setSelectedIndex(0);
        grid.gridx = 1;
        grid.gridy = 0;
        this.panel.add(this.tipo, grid);

        JLabel cliente = new JLabel("Cliente");
        grid.gridx = 0;
        grid.gridy = 1;
        this.panel.add(cliente, grid);

        this.idCliente = new HintTextField("NIF do Cliente");
        grid.gridx = 1;
        grid.gridy = 1;
        this.panel.add(this.idCliente, grid);

        JLabel descricao = new JLabel("Descrição");
        grid.gridx = 0;
        grid.gridy = 1;
        this.panel.add(descricao, grid);

        this.descricao = new HintTextArea("Explique o pedido do cliente");
        grid.gridx = 1;
        grid.gridy = 1;
        grid.gridwidth = 10;
        grid.gridheight = 5;
        this.panel.add(this.descricao, grid);

        this.confirm = new JButton("Registar");
        this.confirm.addActionListener(this);
        grid.gridx = 0;
        grid.gridy = 6;
        grid.gridwidth = 2;
        grid.gridheight = 1;
        this.panel.add(this.confirm, grid);

        this.status = new JLabel("");
        grid.gridx = 0;
        grid.gridy = 7;
        grid.gridwidth = 10;
        this.panel.add(this.status, grid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.confirm)) {
            if (this.sgcr.getPedidos().getClientesId().contains(this.idCliente.getText())) {
                String tipo = (String) this.tipo.getSelectedItem();
                if(tipo == null) return;
                switch(tipo){
                    case ServicoExpresso.Tipo.FORMATAR_PC.getString():
                        break;
                    default:
                        break;
                }
                this.sgcr.registaPedidoOrcamento(
                        this.cliente.getText(),
                        this.trabalhador.getIdTrabalhador(),
                        this.descricao.getText()
                );
                this.status.setText("");
                this.dispose();
            } else {
                this.status.setText("<html><font color=red>Cliente Invalido!</font></html>");
            }
        }
    }
}
