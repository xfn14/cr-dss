package gui.admin;

import sgcr.SGCR;
import utils.gui.HintTextField;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class ListFuncionarioTrabalho extends JFrame implements ActionListener {
    private final SGCR sgcr;
    private final LocalDateTime month;

    private JPanel panel;
    private JTextField search;
    private JTable list;
    private TableModel model;
    private TableRowSorter<TableModel> sorter;

    public ListFuncionarioTrabalho(SGCR sgcr, LocalDateTime month) {
        super("Lista de Receção/Entrega dos Funcionarios");
        this.sgcr = sgcr;
        this.month = month;

        this.initPanel();
        super.add(this.panel, BorderLayout.CENTER);

        super.setSize(700, 800);
        super.setResizable(false);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        super.setVisible(true);
    }

    private void initPanel() {
        this.panel = new JPanel(new BorderLayout());

        this.search = new HintTextField("Procurar");
        this.search.addActionListener(this);
        this.panel.add(this.search, BorderLayout.NORTH);

        String[] header = {"Id Funcionario", "Pedidos Efetuados", "Entregas Efetuadas"};
        Object[][] data = this.sgcr.getNrRececaoEntregaByFuncionario(this.month);
        this.model = new DefaultTableModel(data, header);
        this.list = new JTable(this.model);
        this.sorter = new TableRowSorter<>(this.model);
        this.list.setRowSorter(sorter);
        JScrollPane scrollPane = new JScrollPane(this.list);
        this.panel.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.search)) {
            String in = this.search.getText();
            if (in.length() == 0) this.sorter.setRowFilter(null);
            else this.sorter.setRowFilter(RowFilter.regexFilter(in));
        }
    }
}
