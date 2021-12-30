package gui.admin;

import sgcr.SGCR;
import utils.gui.HintTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AdminPanel extends JPanel implements ActionListener {
    private final SGCR sgcr;

    private final JButton listTrabalhadores;
    private final JButton registar;
    private final JButton listReparacoesTecnico;
    private final JButton listFuncionarioTrabalho;
    private final JButton listIntervencoesTecnico;

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

        this.listTrabalhadores = new JButton("Listar Trabalhadores");
        this.listTrabalhadores.addActionListener(this);
        grid.gridx = 1;
        grid.gridy = 0;
        super.add(this.listTrabalhadores, grid);

        this.listReparacoesTecnico = new JButton("Listar Reparações por Tecnico");
        this.listReparacoesTecnico.addActionListener(this);
        grid.gridx = 0;
        grid.gridy = 1;
        super.add(this.listReparacoesTecnico, grid);

        this.listFuncionarioTrabalho = new JButton("Listar Receção/Entrega Funcionario");
        this.listFuncionarioTrabalho.addActionListener(this);
        grid.gridx = 1;
        grid.gridy = 1;
        super.add(this.listFuncionarioTrabalho, grid);

        this.listIntervencoesTecnico = new JButton("Listar Intervenções Tecnicos");
        this.listIntervencoesTecnico.addActionListener(this);
        grid.gridx = 0;
        grid.gridy = 2;
        super.add(this.listIntervencoesTecnico, grid);

        super.setBackground(new Color(255, 100, 100, 255));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.registar)) {
            new RegistarTrabalhador(this.sgcr);
        } else if (e.getSource().equals(this.listTrabalhadores)) {
            new ListTrabalhadores(this.sgcr);
        } else if (e.getSource().equals(this.listReparacoesTecnico)) {
            LocalDateTime date = getMesAno();
            System.out.println(date);
            if (date != null)
                new ListReparacoesTecnico(this.sgcr, date);
        } else if (e.getSource().equals(this.listFuncionarioTrabalho)) {
            LocalDateTime date = getMesAno();
            if (date != null)
                new ListFuncionarioTrabalho(this.sgcr, date);
        } else if (e.getSource().equals(this.listIntervencoesTecnico)) {
            LocalDateTime date = getMesAno();
            if (date != null)
                new ListIntervencoesTecnico(this.sgcr, date);
        }
    }

    private LocalDateTime getMesAno() {
        JTextField mes = new HintTextField("mes/ano");
        int option = JOptionPane.showConfirmDialog(
                new JFrame(),
                mes,
                "Listar Reparações por Tecnico",
                JOptionPane.YES_NO_OPTION
        );
        if (option == 0) {
            String s = mes.getText();
            if (!s.isBlank() & s.contains("/")) {
                String[] vals = s.split("/");
                try {
                    int m = Integer.parseInt(vals[0]);
                    int a = Integer.parseInt(vals[1]);
                    if (0 < m && m <= 12) {
                        return LocalDateTime.of(LocalDate.of(a, m, 1), LocalTime.now());
                    } else {
                        this.showMesAnoInvalido();
                    }
                } catch (NumberFormatException ex) {
                    this.showMesAnoInvalido();
                }
            }
        }
        return null;
    }

    private void showMesAnoInvalido() {
        JOptionPane.showConfirmDialog(
                new JFrame(),
                "Mes ou ano invalido",
                "Listar Reparações por Tecnico",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
    }
}
