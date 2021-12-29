package gui.pedidos;

import gui.PrettyFrame;
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

public class RegistarSE extends PrettyFrame implements ActionListener {
    private final SGCR sgcr;
    private final Trabalhador trabalhador;
    private final Trabalhador tecnico;

    private JComboBox<String> tipo;
    private JTextField idCliente;
    private JTextArea descricao;
    private JButton confirm;
    private JLabel status;

    public RegistarSE(SGCR sgcr, Trabalhador trabalhador, Trabalhador tecnico) {
        super("Registar Pedido", 700, 500);
        this.sgcr = sgcr;
        this.trabalhador = trabalhador;
        this.tecnico = tecnico;
    }

    @Override
    public void addComponents() {
        String[] tipos = Arrays.stream(ServicoExpresso.Tipo.values())
                .map(ServicoExpresso.Tipo::toString)
                .toArray(String[]::new);
        this.tipo = new JComboBox<>(tipos);
        this.tipo.setSelectedIndex(0);
        this.idCliente = new HintTextField("NIF do Cliente");
        this.descricao = new HintTextArea("Explique o pedido do cliente");
        JScrollPane scroll = new JScrollPane(this.descricao);
        scroll.setPreferredSize(new Dimension(200, 70));
        this.confirm = new JButton("Registar");
        this.status = new JLabel("");

        super.addComponent(new JLabel("Tipo"), 0,0,1,1);
        super.addComponent(this.tipo, 0, 1,1,1);
        super.addComponent(new JLabel("Cliente"), 1,0,1,1);
        super.addComponent(this.idCliente, 1,1,1,1);
        super.addComponent(new JLabel("Descrição"), 2,0,1,1);
        super.addComponent(scroll, 2,1,1, 1);
        super.addComponent(this.confirm, 3,0,2,1);
        super.addComponent(this.status, 4,0,2,1);
    }

    @Override
    public void addActionListener() {
        this.confirm.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.confirm)) {
            if (this.sgcr.isClienteAutenticado(this.idCliente.getText())) {
                String tipo = (String) this.tipo.getSelectedItem();
                if(tipo == null) return;
                switch(tipo){
                    case "Formatar PC":
                        this.sgcr.registarFormatarPC(
                                this.idCliente.getText(),
                                this.trabalhador.getIdTrabalhador(),
                                this.tecnico.getIdTrabalhador(),
                                this.descricao.getText()
                        );
                        break;
                    case "Instalar OS":
                        this.sgcr.registarInstalarOS(
                                this.idCliente.getText(),
                                this.trabalhador.getIdTrabalhador(),
                                this.tecnico.getIdTrabalhador(),
                                this.descricao.getText()
                        );
                        break;
                    case "Substituir Ecra":
                        this.sgcr.registarSubstituirEcra(
                                this.idCliente.getText(),
                                this.trabalhador.getIdTrabalhador(),
                                this.tecnico.getIdTrabalhador(),
                                this.descricao.getText()
                        );
                        break;
                    case "Substituir Bateria":
                        this.sgcr.registarSubstituirBateria(
                                this.idCliente.getText(),
                                this.trabalhador.getIdTrabalhador(),
                                this.tecnico.getIdTrabalhador(),
                                this.descricao.getText()
                        );
                        break;
                    case "Outro":
                        if(this.descricao.getText().isBlank()){
                            this.status.setText("<html><font color=red>Descrição obrigatoria!</font></html>");
                            return;
                        }
                        this.sgcr.registarOutro(
                                this.idCliente.getText(),
                                this.trabalhador.getIdTrabalhador(),
                                this.tecnico.getIdTrabalhador(),
                                this.descricao.getText()
                        );
                        break;
                    default:
                        return;
                }
                this.status.setText("");
                super.dispose();
            } else {
                this.status.setText("<html><font color=red>Cliente Invalido!</font></html>");
            }
        }
    }
}
