package gui.pedidos;

import sgcr.SGCR;
import utils.gui.HintTextField;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListPO extends JFrame implements ActionListener {
    private final SGCR sgcr;

    private JPanel panel;
    private JTextField search;
    private JTable list;
    private TableModel model;
    private TableRowSorter<TableModel> sorter;

    public ListPO(SGCR sgcr) {
        super("Lista de Pedidos de Orçamento");
        this.sgcr = sgcr;

        this.initPanel();
        super.add(this.panel, BorderLayout.CENTER);

        super.setSize(1300, 800);
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

        String[] header = new String[]{"ID", "ID_CLIENT", "ID_FUNCIONARIO", "DESCRIÇÃO", "ORÇAMENTO", "ID_PLANO_TRABALHO", "ESTADO", "ENTREGUE"};
        Object[][] data = this.sgcr.getListPedidoOrcamento();
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
