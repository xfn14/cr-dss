package gui.clientes;

import pedidos.Cliente;
import sgcr.SGCR;

import javax.swing.*;
import java.awt.*;

public class ListClientes extends JFrame {
    private final SGCR sgcr;

    private JPanel panel;
    private JTable list;

    public ListClientes(SGCR sgcr) {
        super("Listar Funcionario");
        this.sgcr = sgcr;

        this.initPanel();
        super.add(this.panel, BorderLayout.CENTER);

        super.setSize(800, 500);
        super.setResizable(false);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        super.setVisible(true);
    }

    private void initPanel() {
        this.panel = new JPanel(new BorderLayout());

        java.util.List<Cliente> lista = this.sgcr.getPedidos().getClientes();
        Object[][] data = new Object[lista.size()][];
        int i = 0;
        for (Cliente c : lista) {
            data[i++] = new Object[]{
                    c.getNome(),
                    c.getNmrUtente(),
                    c.getNmr(),
                    c.getEmail(),
                    c.getPedidos().toString()
            };
        }
        String[] cols = {"Nome", "NIF", "Numero", "Email", "Pedidos"};
        this.list = new JTable(data, cols);
        this.list.setPreferredSize(new Dimension(750, 400));
        JScrollPane scrollPane = new JScrollPane(this.list);
        this.panel.add(scrollPane, BorderLayout.CENTER);
    }
}
