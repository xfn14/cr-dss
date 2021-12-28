package gui.admin;

import sgcr.SGCR;
import trabalhadores.Gestor;
import trabalhadores.Tecnico;
import trabalhadores.Trabalhador;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ListTrabalhadores extends JFrame {
    private final SGCR sgcr;

    private JPanel panel;
    private JTable list;

    public ListTrabalhadores(SGCR sgcr) {
        super("Listar Funcionario");
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

        Map<String, Trabalhador> lista = this.sgcr.getTrabalhadores().getTrabalhadores();
        Object[][] data = new Object[lista.size()][];
        int i = 0;
        for (Trabalhador t : lista.values()) {
            String cargo = t instanceof Gestor ? "Gestor" :
                    t instanceof Tecnico ? "Tecnico" : "Funcionario";
            data[i++] = new Object[]{t.getIdTrabalhador(), cargo};
        }
        String[] cols = {"Username", "Cargo"};
        this.list = new JTable(data, cols);
        this.list.setPreferredSize(new Dimension(600, 400));
        JScrollPane scrollPane = new JScrollPane(this.list);
        this.panel.add(scrollPane, BorderLayout.CENTER);
    }
}
