package gui.pedidos;

import pedidos.Pedido;
import sgcr.SGCR;
import utils.gui.HintTextField;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListPedido extends JFrame implements ActionListener {
    private final SGCR sgcr;

    private JPanel panel;
    private JTextField search;
    private JTable list;
    private TableModel model;
    private TableRowSorter<TableModel> sorter;

    public ListPedido(SGCR sgcr) {
        super("Listar Pedidos");
        this.sgcr = sgcr;

        this.initPanel();
        super.add(this.panel, BorderLayout.CENTER);

        super.setSize(1100, 750);
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

        java.util.List<Pedido> lista = this.sgcr.getPedidos().getPedidos();
        Object[][] data = new Object[lista.size()][];
        int i = 0;
        for (Pedido c : lista) {
            data[i++] = new Object[]{
                    c.getIdPedido(),
                    c.getIdCliente(),
                    c.getIdEquipamento(),
                    c.getIdFuncionario(),
                    c.getEstado().toString(),
                    c.getContactos().toString()
            };
        }
        String[] cols = {"ID", "Cliente_NIF", "ID_Equipamento", "Criado Por", "Estado", "Contactos"};
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
