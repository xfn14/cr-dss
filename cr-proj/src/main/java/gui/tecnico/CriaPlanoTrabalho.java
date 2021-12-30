package gui.tecnico;

import exceptions.InvalidIdException;
import gui.PrettyFrame;
import sgcr.SGCR;
import utils.gui.HintTextArea;
import utils.gui.HintTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CriaPlanoTrabalho extends PrettyFrame implements ActionListener {
    private final SGCR sgcr;
    private String idPedido;
    private String descricaoPO;

    private JTextField horas;
    private JTextField custo;
    private JTextArea descricao;
    private JButton confirm;
    private JLabel status;
    private JButton semReparacao;



    private boolean cancelado;
    private int passoIndex = 0;
    private boolean subPasso;

    public CriaPlanoTrabalho(SGCR sgcr, String idPedido, String tecnico, boolean subpasso) {
        super("Cria Plano Trabalho", 500, 400);
        this.sgcr = sgcr;
        this.idPedido = idPedido;
        this.subPasso = subpasso;
        this.cancelado = false;
        this.descricaoPO = this.sgcr.getDescricaoPedido(this.idPedido);
        this.sgcr.criaPlanosTrabalho(idPedido, tecnico);
        super.addComponent(new JLabel(this.descricaoPO), 0, 0, 2, 1);

        this.confirm = new JButton(this.subPasso ? "Adicionar sub-passo" : "Adicioar passo");
        super.addComponent(this.confirm, 4, 0, 2, 1);
        this.confirm.addActionListener(this);

        this.semReparacao = new JButton("Sem reparacao");
        super.addComponent(this.semReparacao,5,0,2,1);
        this.semReparacao.addActionListener(this);

        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println(sgcr.getPlanoDeTrabalho(idPedido).toString());
                if (!cancelado)sgcr.registaConclusaoPlanoTrabalho(idPedido);
            }
        });
    }

    @Override
    public void addComponents() {
        this.horas = new HintTextField("Horas");
        this.custo = new HintTextField("Custo");
        this.descricao = new HintTextArea("Introduza uma descrição");
        this.status = new JLabel("");

        super.addComponent(new JLabel("Horas"), 1, 0);
        super.addComponent(this.horas, 1, 1);
        super.addComponent(new JLabel("Custo"), 2, 0);
        super.addComponent(this.custo, 2, 1);
        super.addComponent(new JLabel("Descrição"), 3, 0);
        super.addComponent(this.descricao, 3, 1);
        super.addComponent(this.status, 5, 0, 2, 1);
    }

    @Override
    public void addActionListener() {

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.confirm)) {
            try {
                double h = Double.parseDouble(this.horas.getText());
                double c = Double.parseDouble(this.custo.getText());
                if (h > 0 && c > 0) {
                    if (this.subPasso) {
                        this.sgcr.addSubPasso(this.idPedido, this.passoIndex, h, c, this.descricao.getText());
                        this.status.setText("<html><font color=green>Sub-passo adicionado!</font></html>");
                        int option = JOptionPane.showConfirmDialog(
                                new JFrame(),
                                "Pretende adicionar mais sub-passos",
                                "Criar Passo",
                                JOptionPane.YES_NO_OPTION
                        );
                        this.clearFields();
                        if (option != 0) {
                            this.subPasso = false;
                            this.confirm.setText("Adicioar passo");
                            int option2 = JOptionPane.showConfirmDialog(
                                    new JFrame(),
                                    "Pretende adicionar mais passos",
                                    "Criar Passo",
                                    JOptionPane.YES_NO_OPTION
                            );
                            if (option2 != 0) super.dispose();
                            else this.passoIndex++;
                        }
                    } else {
                        this.sgcr.addPasso(this.idPedido, h, c, this.descricao.getText());
                        this.status.setText("<html><font color=greeb>Passo adicionado!</font></html>");
                        int option = JOptionPane.showConfirmDialog(
                                new JFrame(),
                                "Quer dividir em sub-passos?",
                                "Criar Passo",
                                JOptionPane.YES_NO_OPTION
                        );
                        this.clearFields();
                        if (option == 0) {
                            this.subPasso = true;
                            this.confirm.setText("Adicionar sub-passo");
                        } else {
                            this.subPasso = false;
                            this.confirm.setText("Adicioar passo");
                            this.passoIndex++;
                            int option2 = JOptionPane.showConfirmDialog(
                                    new JFrame(),
                                    "Pretende adicionar mais passos",
                                    "Criar Passo",
                                    JOptionPane.YES_NO_OPTION
                            );
                            if (option2 != 0) {
                                super.dispose();
                            }
                        }
                    }
                } else this.status.setText("<html><font color=red>Input Invalido!</font></html>");
            } catch (NumberFormatException ex) {
                this.status.setText("<html><font color=red>Input Invalido!</font></html>");
            }
        }
        if (e.getSource().equals(this.semReparacao)){
            this.cancelado = true;
            try {
                sgcr.registaEquipamentoSemReparacao(idPedido);
            } catch (InvalidIdException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void clearFields() {
        this.status.setText("");
        this.horas.setText("");
        this.custo.setText("");
        this.descricao.setText("");
    }
}
