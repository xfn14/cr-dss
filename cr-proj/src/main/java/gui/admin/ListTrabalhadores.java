package gui.admin;

import sgcr.SGCR;
import trabalhadores.Gestor;
import trabalhadores.Tecnico;
import trabalhadores.Trabalhador;
import utils.gui.HintTextField;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ListTrabalhadores extends JFrame implements ActionListener {
    private final SGCR sgcr;

    private JPanel panel;
    private JTextField search;
    private JTable list;
    private TableModel model;
    private TableRowSorter<TableModel> sorter;

    public ListTrabalhadores(SGCR sgcr) {
        super("Listar Trabalhadores");
        this.sgcr = sgcr;

        this.initPanel();
        super.add(this.panel, BorderLayout.CENTER);

        super.setSize(700, 500);
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

        Map<String, Trabalhador> lista = this.sgcr.getTrabalhadores().getTrabalhadores();
        Object[][] data = new Object[lista.size()][];
        int i = 0;
        for (Trabalhador t : lista.values()) {
            String cargo = t instanceof Gestor ? "Gestor" :
                    t instanceof Tecnico ? "Tecnico" : "Funcionario";
            data[i++] = new Object[]{t.getIdTrabalhador(), cargo};
        }
        String[] cols = {"Username", "Cargo"};
        this.model = new DefaultTableModel(data, cols);
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
